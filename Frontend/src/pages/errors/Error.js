import React from "react";

export default function Error() {
    return (
        <div className="h-screen w-screen flex justify-center items-center">
            <div>
                <p className="text-center text-3xl">Something went wrong!</p>
                <p className="text-center text-2xl">Try again later</p>
            </div>
        </div>
    );
}