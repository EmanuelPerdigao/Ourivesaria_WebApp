import { baseURL } from "../../SharedData";


//######################################## USER TOKEN VALIDATION   #################################################

export const validateUserToken = async () => {

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access'),
    };

    try {
        const response = await sendRequest('GET', baseURL + 'user/auth/token/validate', {}, headers, navigate, location.pathname);

        if(response.status !== 200){
            setLoggedIn(false);
            return;
        }
    } catch (e) {
    };
};

