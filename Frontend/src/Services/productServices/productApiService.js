import {sendRequest} from "../apiGenericService";
import { baseURL } from "../../SharedData";
import { useNavigate, useLocation } from "react-router-dom";


//######################################## GET ALL PRODUCTS TEST  #################################################
export const apigetProducts = async (navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/list', {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};

//######################################## GET ALL CATEGORIES TO ADMIN PAGE #################################################

export const apigetAllCategories = async (navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/category/list', {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};

    //######################################## GET ALL MATERIALS  #################################################

export const apigetAllMaterials = async (navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/material/list', {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};


    //######################################## GET ALL PRODUCTS BY CATEGORY #################################################

export const apigetAllProductsByCategory = async (category, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/listByCategory/' + category.id, {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};


    //######################################## GET ALL PRODUCTS BY CATEGORY and Material #################################################

export const apigetAllProductsByCategoryAndMaterial = async (currentCategory, material, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/listByCategory/' + currentCategory.id + '/material/' + material.id, {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};



    //######################################## GET ALL MATERIALS BY CATEGORY #################################################

export const apigetAllMaterialsByCategory = async (category, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await sendRequest('GET', baseURL + 'api/products/material/listByCategory/' + category.id, {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};


    //######################################## EDIT PRODUCT #################################################

export const apiupdateProduct = async (productId, productName, productImg, productDescription, categoryName, materialName, category, material, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access'),
    };

    const data = {
        id: productId,
        productName: productName,
        productDescription: productDescription,
        productImg: productImg,
        categoryEntity: {
            id: category.id,
            categoryName: category.categoryName
        },
        materialEntity: {
            id: material.id,
            materialName: material.materialName
        }
    };

    try {
        const response = await sendRequest('PUT', baseURL + 'admin/product/update', data, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};


export const apiaddProductByCategory = async (productId, productName, productImg, productDescription, categoryName, materialName, category, material, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access'),
    };

    const data = {
        id: productId,
        productName: productName,
        productDescription: productDescription,
        productImg: productImg,
        categoryEntity: {
            id: category.id,
            categoryName: category.categoryName
        },
        materialEntity: {
            id: material.id,
            materialName: material.materialName
        }
    };

    try {
        const response = await sendRequest('POST', baseURL + 'admin/product/save', data, headers, navigate, location.pathname);

        return response;
    } catch (e) {
        return e;       
    };

};


   //######################################## DELETE PRODUCT #################################################

export const apideleteProduct = async (productId, navigate, location) => {
    
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access'),
    };

    try {
        const response = await sendRequest('DELETE', baseURL + 'admin/product/delete/' + productId, {}, headers, navigate, location.pathname);
        return response;
    } catch (e) {
        return e;       
    };

};

