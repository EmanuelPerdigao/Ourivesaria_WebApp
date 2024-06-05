import { useEffect, useState, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { LoginContext } from "../../App";
import { baseURL } from "../../SharedData";
import { sendRequest } from "../../Services/apiGenericService";

export default function EmailConfirmationPage() {
    const navigate = useNavigate();
    const location = useLocation();

    const [loggedIn, setLoggedIn] = useContext(LoginContext);
    const [loading, setLoading] = useState(true);
    const [emailConfirmed, setEmailConfirmed] = useState(false);

    useEffect(() => {

        const queryParams = new URLSearchParams(location.search);
        const token = queryParams.get('register_token');


        fetch(baseURL + 'user/auth/UserSignUpToken/validate/' + token, {
            method: 'GET',
        })
           
            .then((response) => {

                if (!response.ok) {
                    setLoading(false);
                    setEmailConfirmed(false);
                    throw new Error('Something went wrong! try again!');
                }
                
                return response.json();
            })
            .then((data) => {
                
                localStorage.setItem('access', data.access_token);
                
                setLoggedIn(true);

                alert('You email was confirmed successfully!');
                navigate('/');
            })
            .catch((e) => {
                console.log(e.message);
            });
    }, [location]);

    return (
        <div className="h-screen w-screen flex justify-center items-center">
            {loading ? (
                <p>Loading...</p>
            ) : (
                <>
                    {emailConfirmed ? (
                        <div className="flex flex-col items-center justify-center h-full w-full">
                            <p className="text-4xl font-bold">Email Confirmed</p>
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center h-full w-full">
                            <p className="text-4xl font-bold">Email Not Confirmed</p>
                            <p className="text-2xl font-bold">Please check your email and confirm your account</p>
                        </div>
                    )}
                </>
            )}
        </div>
    );
}
