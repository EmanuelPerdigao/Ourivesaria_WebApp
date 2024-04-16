/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{html,js}"],
    theme: {
        extend: {
            backgroundImage: {                               
                'FrontImage': "url('./Images/FirstImage.jpg')",
            },

            fontFamily: {
                Newsreader: ["Newsreader", "serif"],
                Inter : ["Inter", "sans-serif"],
              },
        },        
    },
    plugins: [],
}