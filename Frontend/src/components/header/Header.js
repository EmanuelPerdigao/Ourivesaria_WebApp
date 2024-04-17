import { Fragment, useContext } from 'react'
import { Disclosure, Menu, Transition } from '@headlessui/react'
import { Bars3Icon, XMarkIcon } from '@heroicons/react/24/outline'
import { LoginContext } from '../../App'

const navigation = [

    { name: 'A nossa história', href: '/#historia'},
    { name: 'Os nossos produtos', href: '/#produtos'},
    { name: 'Onde nos encontrar', href: '/#localizacao'},

]

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function NewHeader() {

    const [loggedIn, setLoggedIn] = useContext(LoginContext);

    return (
        <>
            <Disclosure as="nav" className="group bg-white w-full fixed z-20">
                {/* Disclosure component allows conditional rendering based on state */}
                {({ open }) => (
                    <>
                        {/* Main navigation bar */}
                        <div className="w-screen sm:px-0 md:px-4">
                            <div className="relative flex h-20 items-center justify-between">

                                {/* Mobile menu button */}
                                <div className="absolute inset-y-0 left-[85%] flex items-center sm:hidden">
                                    <Disclosure.Button className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:text-black focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                                        <span className="absolute -inset-0.5"/>
                                        <span className="sr-only">Open main menu</span>
                                        {open ? (
                                            <XMarkIcon className="block h-6 w-6" aria-hidden="true"/>
                                        ) : (
                                            <Bars3Icon className="block h-6 w-6" aria-hidden="true"/>
                                        )}
                                    </Disclosure.Button>
                                </div>

                                {/* Ourivesaria Brand */}
                                <div className='flex items-center ml-8 md:ml-16'>
                                    <a href="/#inicio" className='text-[36px] font-Newsreader font-medium p-0 m-0 text no-underline text-[#CDA135]'>Ourivesaria Ouro</a>
                                </div>

                                {/* Main navigation links */}
                                <div className="flex flex-1 md:justify-end">
                                    <div className="hidden m-0 sm:block">
                                        <div className="flex justify-center mr-20 md:gap-8">
                                            {/* Mapping through navigation items */}
                                            {navigation.map((item) => (
                                                <div key={item.name} className="flex justify-center text-center">
                                                    <a
                                                        href={item.href}
                                                        className={classNames(
                                                            'text-lg font-inter font-medium hover:font-bold text-gray-900 px-4'
                                                        )}
                                                        style={{ textDecoration: 'none' }}
                                                    >
                                                        {item.name}
                                                    </a>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* Panel for mobile view */}
                        <Disclosure.Panel className="sm:hidden">
                            <div className="space-y-1 px-2 pb-3 pt-2 bg-black w-screen h-screen flex flex-col items-center justify-center">
                                {/* Mapping through navigation items for mobile view */}
                                {navigation.map((item) => (
                                    <Disclosure.Button
                                        key={item.name}
                                        as="a"
                                        href={item.href}
                                        className={classNames(
                                            'no-underline text-3xl font-medium text-gray-50 hover:text-blue-700 block rounded-md px-3 py-2 '
                                        )}
                                        aria-current={item.current ? 'page' : undefined}
                                    >
                                        {item.name}
                                    </Disclosure.Button>
                                ))}
                            </div>
                        </Disclosure.Panel>
                    </>
                )}
            </Disclosure>

        </>
    )

}