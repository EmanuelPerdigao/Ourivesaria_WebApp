import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Modal from 'react-bootstrap/Modal';
import { Camera } from 'lucide-react'

function AddProduct(props) {

    const navigate = useNavigate();

    const [productName, setProductName] = useState('');
    const [productDescription, setProductDescription] = useState('');
    const [img, setImg] = useState('');
    const [productCategory, setProductCategory] = useState('');
    const [productMaterial, setProductMaterial] = useState('');


    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <button onClick={() => {
                props.toggleShow();
                props.getAllCategories();
                props.getAllMaterials()
            }}
            className="block mx-auto m-2 bg-gray-900 hover:bg-gray-800 text-white font-bold py-2 px-4 rounded">+ Add Product</button>

            <Modal show={props.show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Product</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={(e) => {
                        //prevent page refresh on submit form
                        e.preventDefault();

                        props.addProductByCategory(props.productId, productName, img, productDescription, productCategory, productMaterial)
                        setProductCategory('');
                        setProductMaterial('');
                        setProductName('');
                        setProductDescription('');
                        setImg('');
                        
                        handleClose();

                    }}
                        id='editmodal'
                    >

                        <div className="flex h-full w-full px-2 py-2 lg:px-2">

                            <form className="h-full w-full flex flex-row space-x-6">

                                <div className=" h-full w-[40%] self-center">

                                    <div className="relative mb-2 block h-60 w-full overflow-hidden bg-gray-100 lg:mb-3">
                                        <img src={img} loading="lazy" alt="Product" className="h-full w-full object-cover object-center" />

                                        <div className="flex justify-end">
                                            <button className='relative -top-10 bg-gray-100 rounded-lg p-2 w-fit h-fit z-30'>
                                                <Camera className='text-black'></Camera>
                                            </button>
                                        </div>

                                    </div>

                                </div>

                                {/* Border separator */}
                                <div className='border'>

                                </div>

                                <div className='flex flex-1 flex-col space-y-2'>

                                    <label for="ProductName" className="block text-sm font-medium leading-6 text-gray-900">Product Name</label>
                                    <div className="mt-2">
                                        <input id="name"
                                            type="text"
                                            placeholder='Nome do produto'
                                            Value={productName}
                                            onChange={(e) => { setProductName(e.target.value) }}
                                            required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                        </input>
                                    </div>

                                    <label for="ProductDescription" className="block text-sm font-medium leading-6 text-gray-900">Product Description</label>
                                    <div className="mt-2">
                                        <input id="ProductDescription"
                                            type="text"
                                            value={productDescription}
                                            onChange={(e) => { setProductDescription(e.target.value) }}
                                            required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                                        </input>
                                    </div>

                                    <div>
                                        <label for="Productimg" className="block text-sm font-medium leading-6 text-gray-900">Imagem</label>
                                        <div className="mt-2">
                                            <input id="Productimg"
                                                type="text"
                                                defaultValue={img}
                                                onChange={(e) => { setImg(e.target.value) }}
                                                required className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></input>
                                        </div>
                                    </div>

                                    <div className='flex flex-row w-full space-x-2'>

                                        <div>
                                            <label htmlFor="ProductCategory" className="block text-sm font-medium leading-6 text-gray-900">Categoria</label>
                                            <div className="mt-2">
                                                <select
                                                    id="ProductCategory"
                                                    value={productCategory}
                                                    onChange={(e) => { setProductCategory(e.target.value) }}
                                                    required
                                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                >
                                                    <option value={productCategory}>{productCategory}</option>

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
                                                    value={productMaterial}
                                                    onChange={(e) => {
                                                        setProductMaterial(e.target.value);
                                                    }}
                                                    required
                                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                >
                                                    <option value={productMaterial}>{productMaterial}</option>

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
                    <button className='bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded' onClick={props.toggleShow}>
                        Close
                    </button>
                    <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded' form="editmodal">
                        Add Product
                    </button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default AddProduct;