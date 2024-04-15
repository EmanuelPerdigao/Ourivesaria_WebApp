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
                <section id='historia' className="w-screen h-full bg-white">

                    <div className="container mx-auto h-full w-full p-12 md:pt-20">

                        <div className="w-full h-full flex flex-col items-center text-black">

                            {/* Title */}
                            <div className=" md:max-w-[65%]">
                                <h2 className="mb-6 text-center  font-medium italic md:text-6xl md:mb-0">Somos especialistas na venda de produtos de ouro e prata.</h2>
                            </div>

                            {/* Description */}
                            <div className=" md:max-w-[60%] pt-10 md:pt-20 text-start md:text-2xl">
                                <div>
                                    <p className="max-w-screen-md">Contando com mais de 50 anos de história, temos mantido a tradição de família, e a nossa paixão pela venda de produtos de ouro e prata.</p>
                                </div>
                                
                                <p className="max-w-screen-md">Localizados na Rua da Ourivesaria, abrimos as portas aos nossos clientes regulares todos os dias, exceto aos domingos.</p>
                                <p className="max-w-screen-md">Temos produtos de variadas marcas, estilos, para todos os gostos, e intervalos de valores também muito abrangentes, desde as peças mais sofisticadas até às peças mais simples e bonitas.</p>
                            </div>

                        </div>

                    </div>


                    {/* Showcase Images */}
                    <div className="flex items-center justify-center gap-4 md:gap-0 w-screen h-full pb-10">
                        <div >
                            <img src={StoreShowCase2} alt="StoreShow" width="800" loading="eager"></img>
                        </div>
                    </div>

                </section>




                {/*######################################## Products Category Section ########################################*/}

                <section id='produtos' className="w-screen h-full md:pt-20">

                    <div className="container mx-auto h-full w-full p-0">

                        <div className="w-full h-full flex flex-col items-left text-black space-y-10">

                            {/* Title */}
                            <div className=" md:max-w-[65%]">
                                <h2 className="text-left  font-medium italic md:text-6xl">O que vendemos?</h2>
                            </div>

                            {/* Description */}
                            <div className="md:max-w-[75%]text-start md:text-2xl">
                                <p className="w-full ">Sendo o nosso principal foco a venda de ouro e prata, dispomos de várias categorias de produtos dentro destes materiais, desde colares, pulseiras, brincos e até anéis.</p>
                                <p className="w-full ">Temos também uma vasta oferta de relógios, decorações, entre outros.</p>
                            </div>

                        </div>

                    </div>

                </section>

                {/* Products Category Showcase Images */}
                <div className="h-full w-full mt-10">
                    <div className="flex flex-col md:flex-row md:flex-wrap w-screen h-full md:h-[70vh]">
                        <div className="w-full md:w-1/3 h-full">
                            <img src={GoldSectionImg} alt="Gold Products" loading="eager" className="object-cover w-full h-full" />
                        </div>
                        <div className="w-full md:w-1/3 h-full">
                            <img src={SilverSectionImg} alt="Silver Products" loading="eager" className="object-cover w-full h-full" />
                        </div>
                        <div className="w-full md:w-1/3 h-full">
                            <img src={OthersSectionImg} alt="Others Products" loading="eager" className="object-cover w-full h-full" />
                        </div>
                    </div>
                </div>


                {/* Explore Products Button */}
                <div className="container flex items-center justify-center w-full h-full py-24 md:py-28">
                    <NavLink to={'/ProductsPage'}>
                        <button className="border-2 rounded-3xl border-black p-2 text-xl md:text-2xl font-semibold text-black hover:scale-105">Explore os nossos produtos</button>
                    </NavLink>
                </div>

                {/* ######################################## Engraving Service Info Section ############################*/}

                <section id='Informações' className="w-screen h-full">
                    <div className="container mx-auto h-full w-full p-0">
                        <div className="w-full h-full flex flex-col items-left space-y-4 text-black">
                            {/* Title */}
                            <div className=" md:max-w-[65%]">
                                <h2 className="mb-6 text-left  font-medium italic md:text-6xl md:mb-0">Serviço de gravação</h2>
                            </div>
                            {/* Description */}
                            <div className="md:max-w-[75%] pt-2 md:pt-20 text-start md:text-2xl">
                                <p className="w-full ">Cada detalhe é uma história! Uma data importante, iniciais ou o nome de alguém especial é uma personalização que nós podemos fornecer, tornando cada peça única e inesquecível.</p>
                            </div>
                        </div>
                        {/* Showcase Image */}
                        <div className="flex flex-wrap w-full items-center justify-center mt-6">
                            <div className="w-full md:w-1/3">
                                <img src={EngravingImageSection} alt="Imagem 1" loading="eager" className="w-full h-300 md:h-full" />
                            </div>
                        </div>
                    </div>
                </section>

                {/*######################################## Location Section ########################################*/}
                <section id='localizacao' className="w-screen h-full bg-white pt-12 md:pt-20 pb-2 space-y-6">
                    <div className="container mx-auto w-full p-0">
                        {/* Location Map */}
                        <div className="flex flex-col w-full justify-around space-y-4 md:space-y-16">
                            <div className="md:max-w-[65%]">
                                <h2 className="mb-6 text-left  font-medium italic md:text-6xl md:mb-0">Onde nos encontrar ?</h2>
                            </div>
                            <div className="">
                                <iframe className="w-full h-96" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d93441.3149049367!2d-9.22447868803797!3d38.75000475554575!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd1ecccc3b1fd217%3A0x71abd39905b41eba!2sParque%20Florestal%20de%20Monsanto!5e0!3m2!1spt-PT!2spt!4v1712684342087!5m2!1spt-PT!2spt"></iframe>
                            </div>
                        </div>
                    </div>
                    {/*######################################## Contact/Opening Hours Section ########################################*/}
                    <div className="container mx-auto border-t-2 border-gray-700"></div>
                    <div id="contactos" className="container mx-auto h-full p-0">
                        <footer className="h-full flex flex-col md:flex-row md:gap-14 justify-left items-start text-black md:p-0 ">
                            {/* Contact Information */}
                            <div className="flex flex-col max-w-[65%] gap-2">
                                <div>
                                    <h2 className="r p-0 font-semibold underline">Contactos</h2>
                                </div>
                                {/* Phone */}
                                <div className="flex flex-row gap-2">
                                    <div>
                                        <Phone/>
                                    </div>
                                    <div>
                                        <p className=" p-0 m-0">+351 915251235</p>
                                    </div>
                                </div>
                                {/* Email */}
                                <div className="flex flex-row gap-2">
                                    <div>
                                        <Mail/>
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

            </body>
        </>
    );
};