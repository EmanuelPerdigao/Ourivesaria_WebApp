import { Fragment, useContext } from 'react'
import { Disclosure, Menu, Transition } from '@headlessui/react'
import { Bars3Icon, XMarkIcon} from '@heroicons/react/24/outline'
import { NavLink } from 'react-router-dom'
import { LoginContext } from '../App'

const navigation = [

    { name: 'A nossa história', href: '/#historia', current: true },
    { name: 'Os nossos produtos', href: '/#produtos', current: false },
    { name: 'Onde nos encontrar', href: '/#localizacao', current: false },

]

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function NewHeader() {

    const [loggedIn, setLoggedIn] = useContext(LoginContext);

    return (
        <>
            <Disclosure as="nav" className="group bg-white w-full fixed z-20">
                {({ open }) => (
                    <>
                        <div className="w-screen px-4 sm:px-6 lg:px-8">
                            <div className="relative flex h-16 items-center justify-between">

                                <div className="absolute inset-y-0 left-[85%] flex items-center sm:hidden">
                                    {/* Mobile menu button*/}
                                    <Disclosure.Button className="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-black hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                                        <span className="absolute -inset-0.5" />
                                        <span className="sr-only">Open main menu</span>
                                        {open ? (
                                            <XMarkIcon className="block h-6 w-6" aria-hidden="true" />
                                        ) : (
                                            <Bars3Icon className="block h-6 w-6" aria-hidden="true" />
                                        )}
                                    </Disclosure.Button>
                                </div>

                                <div className='flex items-center p-6'>
                                    <a href="/#inicio" className='text-[24px] font-Newsreader italic p-0 m-0 text no-underline text-[#CDA135]'>Ourivesaria</a>
                                </div>

                                <div className="flex flex-1 md:justify-end">
                                    <div className="hidden m-0 sm:ml-6 sm:block">
                                        <div className="flex mr-10">
                                            {navigation.map((item) => (
                                                <div>
                                                    <a
                                                        key={item.name}
                                                        href={item.href}
                                                        className={classNames(
                                                            'rounded-md text-sm font-medium hover:scale-110 hover:font-bold text-gray-900 px-4'
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


                        <Disclosure.Panel className="sm:hidden">
                            <div className="space-y-1 px-2 pb-3 pt-2 bg-black w-screen h-screen flex flex-col items-center justify-center">
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
                )
                }
            </Disclosure >

        </>
    )

}