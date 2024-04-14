import axios from 'axios';
import { baseURL } from '../SharedData';

const apiService = axios.create({
    baseURL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const sendRequest = async (method, url, data = {}, headers = {}, navigate, previousUrl, withCredentials = false) => {

    try {
        const response = await apiService({
            method,
            url,
            data,
            headers,
            withCredentials
        });

        return response;

    } catch (error) {

        if (error.code === "ERR_NETWORK") {
            console.log("Network Error");
            navigate('/errorServerDown', {
                state: {
                    previousUrl: previousUrl,
                },
            });
            throw error;
        }


        switch (error.response.status) {
            case 401:
                navigate('/login', {
                    state: {
                        previousUrl: previousUrl,
                    },
                });
                throw error;
            case 403:
                navigate('/login', {
                    state: {
                        previousUrl: previousUrl,
                    },
                });
                throw error;
            case 404:
                navigate('/404', {
                    state: {
                        previousUrl: previousUrl,
                    },
                });
                throw error;
            case 500:
                navigate('/error', {
                    state: {
                        previousUrl: previousUrl,
                    },
                });
                throw error;
            default:
                navigate('/error', {
                    state: {
                        previousUrl: previousUrl,
                    },
                });
                throw error;
        }

    }
};