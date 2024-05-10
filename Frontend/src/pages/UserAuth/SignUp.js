import { useEffect, useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { baseURL } from "../../SharedData";
import { useLocation } from 'react-router-dom';
import base64 from 'react-native-base64'
import { LoginContext } from "../../App";
import { sendRequest } from "../../Services/apiGenericService";

export default function SignUp() {

    const [userName, setUserName] = useState('');
    const [mobileNumber, setMobileNumber] = useState('');
    const [emailId, setEmailId] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [confirmPasswordErrorMessage, setConfirmPasswordErrorMessage] = useState('');

    const [errors, setErrors] = useState({});
    const [generalErrors, setGeneralErrors] = useState();

    const navigate = useNavigate();
    const location = useLocation();

    const [loggedIn, setLoggedIn] = useContext(LoginContext);

    function SignUp(e) {
        e.preventDefault();
        clearErrors();

        if (password != confirmPassword) {
            setConfirmPasswordErrorMessage('Passwords do not match!');
            return;
        }

        fetch(baseURL + 'user/auth/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                userName: userName,
                mobileNumber: mobileNumber,
                emailId: emailId,
                password: password
            })
        })

            .then((response) => {

                if (!response.ok) {
                    return response.json().then(data => {
                        if (data){                            
                            setErrors(data);
                            throw new Error('Something went wrong! Try again!');
                        } else {
                            setGeneralErrors("Something went wrong! Try again!");
                            throw new Error('Something went wrong! Try again!');
                        }
                    });
                }
                return response.json();

            })
            .then((data) => {

                localStorage.setItem('access', data.access);

                setLoggedIn(true);

                navigate(location?.state?.previousUrl
                    ? location.state.previousUrl
                    : '/'
                );
            })
            .catch((e) => {
                console.log(e.message);
            });
    }

    function clearErrors() {        
        setConfirmPasswordErrorMessage('');
    }

    return (

        <div className="flex justify-center items-center h-screen">
            <div className="w-full max-w-xs">
                <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 " onSubmit={SignUp}>
                    
                    {/* Username */}
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="userName">
                            Username
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="userName" type="text" autoComplete="off" onChange={(e) => { setUserName(e.target.value) }} value={userName} placeholder="Username"></input>
                        {errors.userName && <p className="text-red-500 text-xs italic">{errors.userName}</p>}
                    </div>

                    {/* User Phone Number */}
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="mobileNumber">
                            Phone Number
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="mobileNumber" type="tel" autoComplete="off" onChange={(e) => { setMobileNumber(e.target.value) }} value={mobileNumber} placeholder="Numero de telefone"></input>
                        {errors.mobileNumber && <p className="text-red-500 text-xs italic">{errors.mobileNumber}</p>}
                    </div>

                    {/* Email */}
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="emailId">
                            Email
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="emai" type="emailId" autoComplete="off" onChange={(e) => { setEmailId(e.target.value) }} value={emailId} placeholder="Email"></input>
                        {errors.emailId && <p className="text-red-500 text-xs italic">{errors.emailId}</p>}
                    </div>

                    {/* Password */}
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="password">
                            Password
                        </label>
                        <input className="shadow appearance-none border border-red-500 rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" autoComplete="off" onChange={(e) => { setPassword(e.target.value) }} value={password} placeholder="******************"></input>
                        {errors.password && <p className="text-red-500 text-xs italic">{errors.password}</p>}
                    </div>

                    {/* Confirm Password */}
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="confirmPassword">
                            Confirm Password
                        </label>
                        <input className="shadow appearance-none border border-red-500 rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="confirmPassword" type="password" autoComplete="off" onChange={(e) => { setConfirmPassword(e.target.value) }} value={confirmPassword} placeholder="******************"></input>
                        <p className="text-red-500 text-xs italic">{confirmPasswordErrorMessage}</p>
                        <p className="text-red-500 text-xs italic">{generalErrors}</p>
                    </div>

                    <div className="flex justify-center items-center">
                        <button className="bg-orange-500 hover:bg-orange-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                            Create an Account
                        </button>
                    </div>
                </form>

            </div>
        </div>

    )
}