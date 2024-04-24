import { baseURL } from "../SharedData";


//######################################## Wake up server  #################################################
export const apiWakeUpServer = async () => {
    
    fetch(baseURL + 'api/server/wakeup', {
        method: 'GET'
    }).then((response) => {
    }).catch((e) => {
    });

};