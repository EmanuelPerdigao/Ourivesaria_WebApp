import React, { useState, useEffect} from "react";
import { ProductsFilter } from "../../components/products/ProductsPageFilters/ProductsFilter";
import { ProductsSection } from "../../components/products/ProductsSection";
import { useNavigate, useLocation } from "react-router-dom";
import { apigetProducts, apigetAllProductsByCategory, apigetAllProductsByCategoryAndMaterial, apigetAllMaterialsByCategory } from '../../Services/productApiService';


export const ProductsPage = (props) => {

    const navigate = useNavigate();
    const location = useLocation();

    const [products, setProducts] = useState([]);    
    const [loading, setLoading] = useState(true);
    const [materialNavigationOptions, setMaterialNavigationOptions] = useState([]);

    const [navigationCategory, setNavigationCategory] = useState([

        { name: 'Todos', id: 0, requestName: "ALL", current: true },
        { name: 'Anéis', id: 1, requestName: "ANEIS", current: false },
        { name: 'Colares', id: 2, requestName: "COLARES", current: false },
        { name: 'Pulseiras', id: 3, requestName: "PULSEIRAS", current: false },
        { name: 'Brincos', id: 4, requestName: "BRINCOS", current: false },
        { name: 'Relógios', id: 5, requestName: "RELOGIOS", current: false },

    ]);



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


    //######################################## GET ALL PRODUCTS  #################################################


    async function getAllProducts() {

        setLoading(true);

        const response = await apigetProducts(navigate, location)


        if (response) {
            setLoading(false);
            setProducts(response.data);
        }

    }

    //######################################## GET ALL PRODUCTS BY CATEGORY #################################################


    async function getAllProductsByCategory(category) {

        setLoading(true);

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

        setLoading(true);

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

        setLoading(true);

        const response = await apigetAllMaterialsByCategory(category, navigate, location);

        const materialOptions = response.data.map((material) => {
            return { ...material, current: false };
        });

        setMaterialNavigationOptions(materialOptions);
        setLoading(false);

    }


    useEffect(() => {
        
        //Get all products on page load
        getAllProducts();

    }, []);


    return (
        <>
            <div className="w-full h-full overflow-x-hidden bg-white">

                <div className="sm:px-1 md:px-10 lg:px-20 mx-auto">

                    <div className="mt-28 h-content">

                        <ProductsFilter
                            navigationCategory={navigationCategory}
                            changeCurrentCategory={changeCurrentCategory}
                            changeCurrentMaterial={changeCurrentMaterial}
                            getAllProductsByCategory={getAllProductsByCategory}
                            materialNavigationOptions={materialNavigationOptions}
                            getAllProductsByCategoryAndMaterial={getAllProductsByCategoryAndMaterial}>

                        </ProductsFilter>


                        <div className="w-full my-10 border-t-2 border-gray-300">

                        </div>

                        <div>
                            {loading ? (
                                <div className="h-full w-full text-center">
                                    <p>Loading...</p>
                                </div>
                            ) : (
                                <>
                                    {products && products.length > 0 ? (
                                        <ProductsSection products={products}></ProductsSection>
                                    ) : (
                                        <div className="h-full w-full text-center">
                                            <p>De momento não temos produtos desta categoria para apresentar</p>
                                        </div>
                                    )}
                                </>
                            )}
                        </div>
                    </div>


                </div>

            </div>

        </>
    );
};
