import axios from 'axios'
import {SERVER_BASE_URL} from "../config/baseConfig";

export function request(config) {
  const instance = axios.create({
    baseURL: SERVER_BASE_URL,
    timeout: 50000,
  });
  // 添加请求拦截器
  instance.interceptors.request.use(config => {
    return config;
  }, error => {
    return error;
  });
  // 添加响应拦截器
  instance.interceptors.response.use(response => {
    return response.data;
  }, error => {
    return Promise.reject(error.response.data);
  });
  return instance(config);
}