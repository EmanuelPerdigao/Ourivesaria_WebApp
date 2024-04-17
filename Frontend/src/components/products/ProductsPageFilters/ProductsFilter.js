import React, { useState, useEffect, useContext } from "react";

export const ProductsFilter = (props) => {


    return (
        <>

            {/* //#################################### CATEGORY FILTERS ################################################# */}


            <div className="flex flex-col md:flex-row w-full gap-4 md:gap-0">


                <div className="flex flex-col w-full h-full gap-2 md:gap-4">

                    <div>
                        <p className="text-lg font-semibold m-0">Categoria</p>
                    </div>

                    <div>
                        <div className="flex flex-wrap w-full flex-1 flex-row justify-start sm:gap-1 md:gap-3">

                            {props.navigationCategory.map((item, index) => (
                                <button key={index} className={item.current
                                    ? 'bg-gray-900 hover:bg-gray-800 text-white font-semibold py-1 px-2 md:py-1 md:px-3 rounded-full border border-gray-800'
                                    : 'bg-gray-50 hover:bg-gray-400 text-black font-semibold  py-1 px-2 md:py-1 md:px-3 rounded-full border border-gray-800'}
                                    onClick={() => {

                                        props.changeCurrentCategory(item.name);
                                        props.getAllProductsByCategory(item);
                                    }}
                                >
                                    {item.name}
                                </button>
                            ))}

                        </div>

                    </div>
                </div>


                {/* #################################### MATERIAL FILTERS ################################################# */}


                <div className="flex flex-col w-full h-full gap-2 md:gap-4">

                    {props.materialNavigationOptions.length > 0 &&
                        <div className="flex justify-start md:justify-end">
                            <p className="text-lg font-semibold m-0">Material</p>
                        </div>
                    }

                    <div>
                        <div className="flex flex-1 flex-wrap md:flex-auto flex-row justify-start md:justify-end sm:gap-1 md:gap-3">

                            {props.materialNavigationOptions.map((material) => (
                                <button key={material.id} className={material.current
                                    ? 'bg-gray-900 hover:bg-gray-800 text-white font-semibold py-1 px-2 md:py-1 md:px-3 rounded-full border border-gray-800'
                                    : 'bg-gray-50 hover:bg-gray-400 text-black font-semibold  py-1 px-2 md:py-1 md:px-3 rounded-full border border-gray-800'}
                                    onClick={() => {
                                        props.changeCurrentMaterial(material.materialName);
                                        props.getAllProductsByCategoryAndMaterial(material);
                                    }}
                                >
                                    {material.materialName}
                                </button>
                            ))}

                        </div>
                    </div>
                </div>


            </div>

        </>
    );
};
