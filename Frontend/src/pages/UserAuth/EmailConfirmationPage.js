import { useEffect, useState, useContext, useRef } from "react";
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
    const hasFetched = useRef(false);  // Add a ref to track the fetch status


    useEffect(() => {
        if (hasFetched.current) return;  // If already fetched, exit early

        hasFetched.current = true;  // Set the ref to true to indicate the fetch has been initiated

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
                console.log(data);
                localStorage.clear();
                localStorage.setItem('access', data.data.access_token);
                setLoggedIn(true);
                setEmailConfirmed(true);
                alert('You email was confirmed successfully!');
                navigate('/');
            })
            .catch((e) => {
                console.log(e.message);
            });
    }, [location.search, navigate, setLoggedIn]);

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
                            <p className="text-4xl font-bold">Something went wrong</p>
                            <p className="text-2xl font-bold">Email could be already confirmed</p>
                        </div>
                    )}
                </>
            )}
        </div>
    );
}
