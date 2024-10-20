import { useEffect, useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { baseURL } from "../../SharedData";
import { useLocation } from 'react-router-dom';
import base64 from 'react-native-base64'
import { LoginContext } from "../../App";
import { sendRequest } from "../../Services/apiGenericService";

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();
    const location = useLocation();

    const [loggedIn, setLoggedIn] = useContext(LoginContext);

    function Login(e) {
        e.preventDefault();

        fetch(baseURL + 'user/auth/login', {
            method: 'POST',
            headers: { 'Authorization': 'Basic ' + base64.encode(username + ":" + password) }
        })

            .then((response) => {

                if (response.status == "401") {
                    setErrorMessage("Username or Password incorrect!");
                    throw new Error('Username or Password incorrect!');
                }

                if (!response.ok) {
                    setErrorMessage("Something went wrong try again!");
                    throw new Error('Something went wrong! try again!');
                } else {
                    setErrorMessage("Something went wrong try again!");
                }

                return response.json();

            })
            .then((data) => {

                localStorage.setItem('access', data.access_token);

                setLoggedIn(true);

                navigate(location?.state?.previousUrl
                    ? location.state.previousUrl
                    : '/'
                );
            })
            .catch((e) => {
                setErrorMessage((prevErrorMessage) => {
                    const newErrorMessage = "Something went wrong try again!";
                    console.log(newErrorMessage);
                    console.log(e.message);
                    return newErrorMessage;
                });
            });
    }

    return (

        <div className="flex justify-center items-center h-screen">
            <div className="w-full max-w-xs">
                <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 " onSubmit={Login}>
                    <div className="mb-4">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="username">
                            Username
                        </label>
                        <input className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" onChange={(e) => { setUsername(e.target.value) }} value={username} placeholder="Username"></input>
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2" for="password">
                            Password
                        </label>
                        <input className="shadow appearance-none border border-red-500 rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" onChange={(e) => { setPassword(e.target.value) }} value={password} placeholder="******************"></input>
                        <p className="text-red-500 text-xs italic">{errorMessage}</p>
                    </div>
                    <div className="flex justify-center items-center">
                        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                            Login
                        </button>
                    </div>
                </form>

            </div>
        </div>

    )
}