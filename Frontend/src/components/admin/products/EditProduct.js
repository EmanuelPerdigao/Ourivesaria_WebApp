import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function EditProduct(props) {

    const [productName, setProductName] = useState(props.productName);
    const [productDescription, setProductDescription] = useState(props.productDescription);
    const [img, setImg] = useState(props.img);
    const [productCategory, setProductCategory] = useState(props.categoryName);
    const [productMaterial, setProductMaterial] = useState(props.materialName);

    const [productNamePlaceHolder, setProductNamePlaceHolder] = useState(props.productName);
    const [imgPlaceHolder, setImgPlaceHolder] = useState(props.img);
    const [descriptionPlaceHolder, setDescriptionPlaceHolder] = useState(props.productDescription);
    const [categoryPlaceHolder, setCategoryPlaceHolder] = useState(props.categoryName);
    const [materialPlaceHolder, setMaterialPlaceHolder] = useState(props.materialName);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    return (
        <>

            <button onClick={() => {
                handleShow();
                props.getAllCategories();
                props.getAllMaterials()
            }}
                className="px-4 py-1 text-sm text-purple-600 font-semibold rounded-full border border-purple-200 hover:text-white hover:bg-purple-600 hover:border-transparent focus:outline-none focus:ring-2 focus:ring-purple-600 focus:ring-offset-2">Edit</button>

            <Modal show={show} onHide={handleClose} centered size='xl' style={{ height: '100vh' }}>
                <Modal.Header closeButton>
                    <Modal.Title>Product Information</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={(e) => {
                        //prevent page refresh on submit form
                        e.preventDefault();

                        props.updateProduct(props.productId, productNamePlaceHolder, imgPlaceHolder, descriptionPlaceHolder, categoryPlaceHolder, materialPlaceHolder);
                        
                        handleClose();
                    }}
                        id='editmodal'
                    >


                        <div className="flex h-full w-full px-2 py-2 lg:px-2">

                            <form className="h-full w-full flex flex-row space-x-6">

                                <div className=" h-full w-[40%] self-center">

                                    <div>
                                        <img src={imgPlaceHolder} loading="lazy" alt="Product" className="h-full w-full object-cover object-center" />                                   
                                    </div>

                                </div>

                                {/* Border separator */}
                                <div className='border'>

                                </div>

                                <div className='flex flex-1 flex-col space-y-2'>

                                    <label for="ProductName" className="block text-sm font-medium leading-6 text-gray-900">Product Name</label>
                                    <div className="mt-2">
                                        <input id="ProductName"
                                            type="text"
                                            placeholder='Product name'
                                            autocomplete="email"
                                            value={productNamePlaceHolder}
                                            onChange={(e) => { setProductNamePlaceHolder(e.target.value) }}
                                            required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                        </input>
                                    </div>

                                    <label for="ProductDescription" className="block text-sm font-medium leading-6 text-gray-900">Product Description</label>
                                    <div className="mt-2">
                                        <input id="ProductDescription"
                                            type="text"
                                            placeholder='Product description'
                                            value={descriptionPlaceHolder}
                                            onChange={(e) => { setDescriptionPlaceHolder(e.target.value) }}
                                            required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                        </input>
                                    </div>

                                    <div>
                                        <label for="Productimg" className="block text-sm font-medium leading-6 text-gray-900">Imagem</label>
                                        <div className="mt-2">
                                            <input id="Productimg"
                                                type="text"
                                                placeholder='Product image'
                                                defaultValue={imgPlaceHolder}
                                                onChange={(e) => { setImgPlaceHolder(e.target.value) }}
                                                required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                                        </div>
                                    </div>

                                    <div className='flex flex-row w-full space-x-2'>

                                        <div>
                                            <label htmlFor="ProductCategory" className="block text-sm font-medium leading-6 text-gray-900">Categoria</label>
                                            <div className="mt-2">
                                                <select
                                                    id="ProductCategory"
                                                    value={categoryPlaceHolder}
                                                    onChange={(e) => { setCategoryPlaceHolder(e.target.value) }}
                                                    required
                                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                >
                                                    <option value={categoryPlaceHolder}>{categoryPlaceHolder}</option>

                                                    {props.allCategoryOptionsAvailable.map((category) => (
                                                        <option value={category.categoryName}>{category.categoryName}</option>
                                                    ))}
                                                </select>
                                            </div>
                                        </div>

                                        <div>
                                            <label for="ProductMaterial" className="block text-sm font-medium leading-6 text-gray-900">Material</label>
                                            <div className="mt-2">
                                                <select
                                                    id="MaterialCategory"
                                                    value={materialPlaceHolder}
                                                    onChange={(e) => { 
                                                        setMaterialPlaceHolder(e.target.value);                                                    
                                                    }}
                                                    required
                                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                >
                                                    <option value={materialPlaceHolder}>{materialPlaceHolder}</option>

                                                    {props.allMaterialOptionsAvailable.map((material) => (
                                                        <option value={material.materiaName}>{material.materialName}</option>
                                                    ))}
                                                </select>
                                            </div>
                                        </div>

                                    </div>

                                </div>

                            </form>

                        </div>
                    </form>


                </Modal.Body>
                <Modal.Footer>
                    <button className='bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded' onClick={() => {
                        setProductNamePlaceHolder(productName);
                        setDescriptionPlaceHolder(productDescription);
                        setCategoryPlaceHolder(productCategory);
                        setImgPlaceHolder(img);
                        handleClose();
                    }}>
                        Close
                    </button>
                    <button className='bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded' onClick={() => props.deleteProduct(props.productId)}>
                        Delete Product
                    </button>
                    <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded' form="editmodal">
                        Save Product
                    </button>

                </Modal.Footer>
            </Modal>
        </>
    );
}

export default EditProduct;