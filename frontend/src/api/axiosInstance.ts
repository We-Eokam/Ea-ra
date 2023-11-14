import axios  from "axios"; // AxiosInstance 타입 추가

const axiosInstance = () => {

  const instance = axios.create({
    baseURL: 'https://dev.ea-ra.com/api',
    timeout: 10000,
    withCredentials: true,
  });

  return instance;
};

export default axiosInstance;