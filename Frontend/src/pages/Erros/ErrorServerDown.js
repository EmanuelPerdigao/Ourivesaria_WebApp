import React from "react";

export default function Error() {
    return (
        <div className="h-screen w-screen flex justify-center items-center">
            <div>
                <p className="text-center text-3xl">Our servers are down!</p>
                <p className="text-center text-2xl">We will get back to you soon! Try again later</p>                
            </div>

        </div>
    );
}