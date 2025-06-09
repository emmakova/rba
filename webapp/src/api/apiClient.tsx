import { Configuration, UserControllerApi, CardStatusControllerApi } from './clients';



const config = new Configuration({
    basePath: import.meta.env.VITE_API_BASE_URL,
});


export const userApi = new UserControllerApi(config);
export const cardStatusApi = new CardStatusControllerApi(config);