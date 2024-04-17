import React, { useState, useEffect, useContext } from "react";

export const ProductsSection = (props) => {

    return (
        <>
             <div className="grid gap-x-5 md:gap-x-10 gap-y-10 p-10 md:p-0 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                {props.products.map((product) => (
                    <div key={product.id} className="flex flex-col border h-[500px] w-full">
                        <a className="group relative block h-full w-full overflow-hidden">
                            <img src={product.productImg} loading="lazy" alt="Product" className="h-full w-full object-cover object-center transition duration-200 group-hover:scale-110" />
                        </a>
                        <div className="flex flex-col p-4 bg-gray-50">
                            <div>
                                <p className="text-xl font-semibold m-0 overflow-auto">
                                    {product.productName}
                                </p>
                            </div>
                            <div className="mt-2">
                                <p className="text-base font-normal m-0 text-gray-900 overflow-auto">
                                    {product.productDescription}
                                </p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
};
