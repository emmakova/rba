import { Configuration, UserControllerApi, CardStatusControllerApi } from './clients';



const config = new Configuration({
    basePath: 'http://localhost:8080',
});


export const userApi = new UserControllerApi(config);
export const cardStatusApi = new CardStatusControllerApi(config);