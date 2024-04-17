import React, { useState, Fragment, useEffect } from "react";
import { Mail, MapPin, Phone, mail, phone } from 'lucide-react'
import { NavLink } from 'react-router-dom'
import StoreShowCase2 from '../../Images/StoreShowcase2.jpg'
import GoldSectionImg from '../../Images/GoldSection.jpg'
import SilverSectionImg from '../../Images/SilverSection.jpg'
import OthersSectionImg from '../../Images/OtherSection.jpg'
import EngravingImageSection from '../../Images/EngravingImageSection.jpg'

export const OurivesariaLandingPage = () => {

    const showAlert = () => {
        alert('Thank you for visiting my project! Please note that, due to the free version of hosting, the server automatically shuts down after some time. It will come back on after you visit the website again, which may take a few minutes. If you have any questions or concerns, please send me a message. Thanks!');
    };

    useEffect(() => {
        showAlert();
    }, []);

    return (
        <>
            <body className="overflow-x-hidden ">

                {/*######################################## Front Image Section ########################################*/}
                <section id="inicio" className="w-screen h-screen bg-center bg-cover bg-blend-overlay  bg-FrontImage">

                </section>

                {/*######################################## History Section ########################################*/}
                <section id='historia' className="w-screen h-full">

                    <div className="h-full w-full px-8 md:px-12 pt-20 md:pt-52">

                        <div className="w-full h-full flex flex-col items-center text-left md:text-center font text-black">

                            {/* Title */}
                            <div className=" md:max-w-[45%]">
                                <h2 className="m-0 font-Newsreader text-1xl md:text-6xl">Somos especialistas na venda de produtos de ouro e prata.</h2>
                            </div>

                            {/* Description */}
                            <div className="flex flex-col font-Inter content-center md:max-w-[40%] pt-10 md:pt-20 text-sm md:text-xl">
                                <div className="w-full h-full">
                                    <p className="">Contando com mais de 50 anos de história, temos mantido a tradição de família, e a nossa paixão pela venda de produtos de ouro e prata.</p>
                                </div>
                                <div className="w-full h-full">
                                    <p className="">Localizados na Rua da Ourivesaria, abrimos as portas aos nossos clientes regulares todos os dias, exceto aos domingos.</p>
                                </div>
                                <div className="w-full h-full">
                                    <p className="">Temos produtos de variadas marcas, estilos, para todos os gostos, e intervalos de valores também muito abrangentes, desde as peças mais sofisticadas até às peças mais simples e bonitas.</p>
                                </div>

                            </div>

                        </div>

                    </div>

                </section>




                {/*######################################## Products Category Section ########################################*/}

                <section id='produtos' className="w-full h-full pt-20 md:pt-52">
                    <div className="h-full md:h-[600px] w-[80%] mx-auto">
                        <div className="flex flex-col md:flex-row space-y-2 md:space-y-0 space-x-0 md:space-x-4 w-full h-full">
                            <div className="w-full md:w-1/3 h-full">
                                <img src={GoldSectionImg} alt="Gold Products" loading="eager" className="object-cover w-full h-full hover:shadow-xl" />
                            </div>
                            <div className="w-full md:w-1/3 h-full">
                                <img src={SilverSectionImg} alt="Silver Products" loading="eager" className="object-cover w-full h-full hover:shadow-xl" />
                            </div>
                            <div className="w-full md:w-1/3 h-full">
                                <img src={OthersSectionImg} alt="Others Products" loading="eager" className="object-cover w-full h-full hover:shadow-xl" />
                            </div>
                        </div>
                    </div>

                </section>


                {/* Explore Products Button */}
                <div className="container flex items-center justify-center w-full h-full py-12 md:py-20">
                    <p className="m-0 text-xl md:text-3xl font-Newsreader font-medium text-black">
                        conheça todos os produtos {' '}
                        <NavLink to={'/ProductsPage'} className="underline cursor-pointer hover:text-blue-700 text-gray-950 italic">aqui</NavLink>.
                    </p>
                </div>


                {/* ######################################## Engraving Service Info Section ############################*/}


                <section id='informação' className="w-screen h-full pt-20 md:pt-46">

                    <div className=" h-full w-full px-8 md:px-28">

                        <div className="w-full h-full flex flex-col items-center text-black">

                            {/* Title */}
                            <div className=" md:max-w-[65%]">
                                <h2 className="mb-6 md:mb-0 text-center font-Newsreader text-1xl md:text-6xl ">Serviço de gravação</h2>
                            </div>

                            {/* Description */}
                            <div className="font-Inter content-center md:max-w-[35%] pt-10 md:pt-20 text-center md:text-xl">
                                <div className="w-full h-full">
                                    <p className="">Cada detalhe é uma história! Uma data importante, iniciais ou o nome de alguém especial é uma personalização que nós podemos fornecer, tornando cada peça única e inesquecível.</p>
                                </div>
                            </div>

                            {/* Showcase Image */}
                            <div className="flex flex-wrap w-full items-center justify-center mt-20">
                                <div className="w-full h-full">
                                    <img src={EngravingImageSection} alt="Engraving Service Jewellery" loading="eager" className="w-full h-400 md:h-full" />
                                </div>
                            </div>
                        </div>
                    </div>

                </section>


                {/*######################################## Location Section ########################################*/}
                <section id='localizacao' className="w-screen h-full pt-12 md:pt-48 pb-16 space-y-6">
                    <div className="container mx-auto w-full p-0">
                        {/* Location Map */}
                        <div className="flex flex-col w-full space-y-4 md:space-y-16 items-center text-center">

                            <div className=" md:max-w-[65%]">
                                <h2 className="m-0 font-Newsreader text-1xl md:text-6xl">Onde nos encontrar ?</h2>
                            </div>

                            <div className="w-full h-full">
                                <iframe className="w-full h-96 md:h-[600px]" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d93441.3149049367!2d-9.22447868803797!3d38.75000475554575!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd1ecccc3b1fd217%3A0x71abd39905b41eba!2sParque%20Florestal%20de%20Monsanto!5e0!3m2!1spt-PT!2spt!4v1712684342087!5m2!1spt-PT!2spt"></iframe>
                            </div>
                        </div>
                    </div>
                    {/*######################################## Contact/Opening Hours Section ########################################*/}
                    <div className="container mx-auto border-gray-700 pt-2"></div>
                    <div id="contactos" className="container mx-auto h-full p-0">
                        <footer className="h-full flex flex-col md:flex-row md:gap-14 justify-between items-start text-black md:p-0 ">
                            {/* Contact Information */}
                            <div className="flex flex-col max-w-[65%] gap-2">
                                <div>
                                    <h2 className="r p-0 font-semibold underline">Contactos</h2>
                                </div>
                                {/* Phone */}
                                <div className="flex flex-row gap-2">
                                    <div>
                                        <Phone />
                                    </div>
                                    <div>
                                        <p className=" p-0 m-0">+351 915251235</p>
                                    </div>
                                </div>
                                {/* Email */}
                                <div className="flex flex-row gap-2">
                                    <div>
                                        <Mail />
                                    </div>
                                    <div>
                                        <p className="r p-0 m-0">Ourivesaria@gmail.com</p>
                                    </div>
                                </div>
                                {/* Address */}
                                <div className="flex flex-row gap-2">
                                    <div>
                                        <MapPin />
                                    </div>
                                    <div>
                                        <a className="p-0 m-0 no-underline text-black" href="https://maps.app.goo.gl/veQtbYQ3qk95Bgiv6" target="_blank" rel="noreferrer">Rua da Ourivesaria, nº 123, 1234-567, Lisboa</a>
                                    </div>
                                </div>
                            </div>
                            {/* Opening Hours */}
                            <div className="flex flex-col max-w-[65%] gap-2">
                                <div><h2 className="r p-0 font-semibold underline">Horários</h2></div>
                                {/* Weekdays */}
                                <div className="flex flex-row gap-2">
                                    <div><p className=" p-0 m-0">seg - sex: 09:00-19:00</p></div>
                                </div>
                                {/* Saturday */}
                                <div className="flex flex-row gap-2">
                                    <div><p className="r p-0 m-0">sáb.: 09:00-13:00</p></div>
                                </div>
                                {/* Sunday */}
                                <div className="flex flex-row gap-2">
                                    <div><p className="r p-0 m-0">dom.: Fechado</p></div>
                                </div>
                            </div>
                        </footer>
                    </div>
                </section>

            </body >
        </>
    );
};