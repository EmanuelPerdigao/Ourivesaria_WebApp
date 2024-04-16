import { useEffect, useState} from 'react';
import { useNavigate, useLocation } from "react-router-dom";
import { sendRequest } from '../../services/apiGenericService';
import { baseURL } from '../../SharedData';
import { ProductsFilter } from '../../components/products/ProductsPageFilters/ProductsFilter';
import EditProduct from '../../components/admin/products/EditProduct';
import AddProduct from '../../components/admin/products/AddProduct';
import { apigetProducts, apigetAllCategories, apigetAllMaterials, apigetAllProductsByCategory, apigetAllProductsByCategoryAndMaterial, apigetAllMaterialsByCategory, apiaddProductByCategory, apiupdateProduct, apideleteProduct } from '../../services/productApiService';

export default function NewAdminPage() {


    const location = useLocation();
    const navigate = useNavigate();

    const [products, setProducts] = useState([]);
    const [materialNavigationOptions, setMaterialNavigationOptions] = useState([]);
    const [allMaterialOptionsAvailable, setallMaterialOptionsAvailable] = useState([]);
    const [allCategoryOptionsAvailable, setallCategoryOptionsAvailable] = useState([]);
    const [errorStatus, setErrorStatus] = useState();
    const [loading, setLoading] = useState(true);
    const [show, setShow] = useState(false);


    //TODO - Request to the server for all categories
    const [navigationCategory, setNavigationCategory] = useState([

        { name: 'Todos', id: 0, requestName: "ALL", current: true },
        { name: 'Anéis', id: 1, requestName: "ANEIS", current: false },
        { name: 'Colares', id: 2, requestName: "COLARES", current: false },
        { name: 'Pulseiras', id: 3, requestName: "PULSEIRAS", current: false },
        { name: 'Brincos', id: 4, requestName: "BRINCOS", current: false },
        { name: 'Relógios', id: 5, requestName: "RELOGIOS", current: false },

    ]);


    function toggleShow() {
        setShow(!show);
    }


    //######################################## Change the current category #################################################

    function changeCurrentCategory(newCurrentCategoryOption) {

        const newNavigationCategory = navigationCategory.map((category) => {
            if (category.name === newCurrentCategoryOption) {
                return { ...category, current: true };
            } else {
                return { ...category, current: false };
            }
        });


        setNavigationCategory(newNavigationCategory);
    }


    //######################################## Reset filters #################################################

    function resetFilters() {

        const newNavigationCategory = navigationCategory.map((category) => {
            if (category.requestName === "ALL") {
                return { ...category, current: true };
            } else {
                return { ...category, current: false };
            }
        });

        setNavigationCategory(newNavigationCategory);

        setMaterialNavigationOptions([]);
    }



    //######################################## Change the current material #################################################

    function changeCurrentMaterial(newCurrentMaterialOption) {

        const newNavigationMaterial = materialNavigationOptions.map((material) => {
            if (material.materialName === newCurrentMaterialOption) {
                return { ...material, current: true };
            } else {
                return { ...material, current: false };
            }
        });

        setMaterialNavigationOptions(newNavigationMaterial);
    }



    //######################################## GET CURRENT CATEGORY #################################################
    const getCurrentCategory = () => {
        return navigationCategory.find(category => category.current);
    };


    //######################################## GET SPECIFIC CATEGORY FROM ALL CATEGORIES AVAILABLE #################################################
    const getCategory = (categoryName) => {
        return allCategoryOptionsAvailable.find(category => category.categoryName === categoryName);
    };


    //######################################## GET SPECIFIC Material FROM ALL MATERIALS AVAILABLE#################################################

    const getMaterial = (materialName) => {
        return allMaterialOptionsAvailable.find(material => material.materialName === materialName);
    };


    //######################################## GET ALL PRODUCTS  #################################################

    async function getAllProducts() {

        resetFilters();

        const response = await apigetProducts(navigate, location)

        setLoading(false);

        if (response) {
            setProducts(response.data);
        }

    }


    //######################################## GET ALL CATEGORIES TO ADMIN PAGE #################################################


    async function getAllCategories() {

        const response = await apigetAllCategories(navigate, location)

        setLoading(false);

        if (response) {
            setallCategoryOptionsAvailable(response.data);
        }

    }


    //######################################## GET ALL MATERIALS  #################################################

    async function getAllMaterials() {

        const response = await apigetAllMaterials(navigate, location)

        setLoading(false);

        if (response.data) {
            setallMaterialOptionsAvailable(response.data);
        }

    }

    //######################################## GET ALL PRODUCTS BY CATEGORY #################################################


    async function getAllProductsByCategory(category) {

        if (category.id === 0) {
            getAllProducts();
            setMaterialNavigationOptions([]);
            return;
        }

        const response = await apigetAllProductsByCategory(category, navigate, location);

        setProducts(response.data);
        setLoading(false);

        if (response.data.length > 0) {
            getAllMaterialsByCategory(category);
        } else {
            setMaterialNavigationOptions([]);
        }

    }

    //######################################## GET ALL PRODUCTS BY CATEGORY and Material #################################################

    async function getAllProductsByCategoryAndMaterial(material) {

        const currentCategory = getCurrentCategory();

        if (!currentCategory) {
            console.error("No category selected.");
            return;
        }

        const response = await apigetAllProductsByCategoryAndMaterial(currentCategory, material, navigate, location);

        setProducts(response.data);
        setLoading(false);

    }


    //######################################## GET ALL MATERIALS BY CATEGORY #################################################

    async function getAllMaterialsByCategory(category) {


        const response = await apigetAllMaterialsByCategory(category, navigate, location);

        const materialOptions = response.data.map((material) => {
            return { ...material, current: false };
        });

        setMaterialNavigationOptions(materialOptions);
        setLoading(false);

    }


    //######################################## EDIT PRODUCT #################################################


    async function updateProduct(productId, productName, productImg, productDescription, categoryName, materialName) {

        const category = getCategory(categoryName);
        const material = getMaterial(materialName);

        const response = await apiupdateProduct(productId, productName, productImg, productDescription, categoryName, materialName, category, material, navigate, location);

        getAllProducts();
        setLoading(false);

    }


    //######################################## ADD NEW PRODUCT BY CATEGORY #################################################  


    async function addProductByCategory(productId, productName, productImg, productDescription, categoryName, materialName) {

        const category = getCategory(categoryName);
        const material = getMaterial(materialName);

        const response = await apiaddProductByCategory(productId, productName, productImg, productDescription, categoryName, materialName, category, material, navigate, location);

        getAllProducts();
        setLoading(false);
        toggleShow();

    }


    //######################################## DELETE PRODUCT #################################################

    async function deleteProduct(productId) {


        const response = await apideleteProduct(productId, navigate, location);

        getAllProducts();
        setLoading(false);

    }


    //######################################## Authentication #################################################

    const authAdmin = async () => {

        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access'),
        };

        try {

            const response = await sendRequest('GET', baseURL + 'admin/auth/', {}, headers, navigate, location.pathname);

            getAllProducts();


            setLoading(false);

        } catch (e) {
            setErrorStatus(e);
            setLoading(false);
        };
    };

    useEffect(() => {
        authAdmin();
    }, []);


    return (
        <>
            <div className="w-full h-full overflow-x-hidden bg-white">

                <div className="container mx-auto">

                    <div className="mt-20 h-content">


                        <ProductsFilter
                            navigationCategory={navigationCategory}
                            changeCurrentCategory={changeCurrentCategory}
                            changeCurrentMaterial={changeCurrentMaterial}
                            getAllProductsByCategory={getAllProductsByCategory}
                            materialNavigationOptions={materialNavigationOptions}
                            getAllProductsByCategoryAndMaterial={getAllProductsByCategoryAndMaterial}>

                        </ProductsFilter>


                        <div className="w-full my-6 border-t-2 border-gray-300">

                        </div>

                        <div>                            
                            {products && products.length > 0 ? (

                                <div className="grid gap-x-10 gap-y-10 p-10 md:p-0 sm:grid-cols-2 md:gap-x-20 lg:grid-cols-2 xl:grid-cols-3">
                                    {products.map((product) => (
                                        <div key={product.id} className="flex flex-col border h-full w-full">

                                            <a className="group relative block h-full w-full overflow-hidden">
                                                <img src={product.productImg} loading="lazy" alt="Product" className="h-full w-full object-cover object-center transition duration-200 group-hover:scale-110" />
                                            </a>

                                            <div className="flex flex-col p-4 bg-gray-50">
                                                <div>
                                                    <p className="text-xl font-semibold m-0 overflow-auto">
                                                        {product.productName}
                                                    </p>
                                                </div>
                                                <div className="mt-2 overflow-auto">
                                                    <p className="text-base font-normal m-0 text-gray-900">
                                                        {product.productDescription}
                                                    </p>
                                                </div>

                                                <div className="flex items-center justify-center mt-2">
                                                    {/* Carrego o edit dos produtos */}
                                                    <EditProduct
                                                        productId={product.id}
                                                        productName={product.productName}
                                                        productDescription={product.productDescription}
                                                        img={product.productImg}
                                                        categoryName={product.categoryEntity.categoryName}
                                                        materialName={product.materialEntity.materialName}
                                                        allMaterialOptionsAvailable={allMaterialOptionsAvailable}
                                                        allCategoryOptionsAvailable={allCategoryOptionsAvailable}
                                                        getAllCategories={getAllCategories}
                                                        getAllMaterials={getAllMaterials}
                                                        updateProduct={updateProduct}
                                                        deleteProduct={deleteProduct}>
                                                        Edit Product
                                                    </EditProduct>
                                                </div>

                                            </div>
                                        </div>
                                    ))}
                                </div>
                            ) : (
                                <div className="h-full w-full text-center">
                                    <p>De momento não temos produtos desta categoria para apresentar</p>
                                </div>
                            )}
                        </div>

                        <AddProduct
                            allMaterialOptionsAvailable={allMaterialOptionsAvailable}
                            allCategoryOptionsAvailable={allCategoryOptionsAvailable}
                            getAllCategories={getAllCategories}
                            getAllMaterials={getAllMaterials}
                            addProductByCategory={addProductByCategory}
                            show={show}
                            toggleShow={toggleShow}
                        />

                    </div>
                </div>
            </div>
        </>
    );
}
