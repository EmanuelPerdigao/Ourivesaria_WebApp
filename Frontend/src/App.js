import './index.css';
import { createContext, useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import NotFound from './components/NotFound';
import { OurivesariaLandingPage } from './pages/LadingPage/OurivesariaLandingPage';
import AdminPage from './pages/admin/AdminPage';
import { ProductsPage } from './pages/products/ProductsPage';
import Error from './pages/Erros/Error'
import ErrorServerDown from './pages/Erros/ErrorServerDown'

import Login from './pages/UserAuth/Login';

export const LoginContext = createContext();

function App() {

    const [loggedIn, setLoggedIn] = useState(localStorage.access ? true : false);

    function changeLoggedIn(value) {
        setLoggedIn(value);

        if (value === false) {
            localStorage.clear();
        }
    }

    return (
        <LoginContext.Provider value={[loggedIn, changeLoggedIn]}>
            <BrowserRouter>
                <Header>
                </Header>
                <Routes>
                    <Route path='/' element={<OurivesariaLandingPage />} />
                    <Route path='/admin' element={<AdminPage />} />
                    <Route path='/login' element={<Login />} />                    
                    <Route path='/404' element={<NotFound />} />
                    <Route path='/error' element={<Error/>} />
                    <Route path='/errorServerDown' element={<ErrorServerDown/>} />
                    <Route path='/NewHeader' element={<Header />} />                                      
                    <Route path='/productspage' element={<ProductsPage/>} />
                    <Route path='/*' element={<NotFound />} />
                </Routes>

            </BrowserRouter>
        </LoginContext.Provider>
    );
}

export default App;
