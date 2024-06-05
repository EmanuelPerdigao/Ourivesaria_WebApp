import './index.css';
import { createContext, useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/header/Header';
import NotFound from './pages/errors/NotFound';
import { OurivesariaLandingPage } from './pages/landingPage/OurivesariaLandingPage';
import AdminPage from './pages/admin/AdminPage';
import { ProductsPage } from './pages/products/ProductsPage';
import Error from './pages/errors/Error';
import ErrorServerDown from './pages/errors/ErrorServerDown';
import Login from './pages/UserAuth/Login';
import SignUp from './pages/UserAuth/SignUp';
import EmailConfirmationPage from './pages/UserAuth/EmailConfirmationPage';
import { baseURL } from "./SharedData";

export const LoginContext = createContext();

function App() {

    const [loggedIn, setLoggedIn] = useState(localStorage.access ? true : false);

    function changeLoggedIn(value) {
        setLoggedIn(value);

        if (value === false) {
            localStorage.clear();
        }
    }


    //######################################## User Token Validation #################################################

    const validateUserToken = async () => {
        const headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access'),
        };
    
        try {
            const response = await fetch(baseURL + 'user/auth/token/validate', {
                method: 'GET',
                headers: headers
            });
    
            if (!response.ok) {
                // Handle unauthorized or other error responses
                setLoggedIn(false);
                localStorage.clear();
                return;
            }

        } catch (error) {
            // Handle fetch error if needed
            console.error('Error fetching user token:', error);
        }
    };

    useEffect(() => {
        if (localStorage.access) {
            validateUserToken();
        }
    })

    return (
        <LoginContext.Provider value={[loggedIn, changeLoggedIn]}>
            <BrowserRouter>
                <Header>
                </Header>
                <Routes>
                    <Route path='/' element={<OurivesariaLandingPage />} />
                    <Route path='/admin' element={<AdminPage />} />
                    <Route path='/login' element={<Login />} />
                    <Route path='/register' element={<SignUp />} />
                    <Route path='/404' element={<NotFound />} />
                    <Route path='/error' element={<Error />} />
                    <Route path='/errorServerDown' element={<ErrorServerDown />} />
                    <Route path='/NewHeader' element={<Header />} />
                    <Route path='/productspage' element={<ProductsPage />} />
                    <Route path='/user/verify*' element={<EmailConfirmationPage />} />
                    <Route path='/*' element={<NotFound />} />
                </Routes>

            </BrowserRouter>
        </LoginContext.Provider>
    );
}

export default App;
