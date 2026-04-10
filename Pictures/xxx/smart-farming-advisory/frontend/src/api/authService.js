import api from './api';

const login = async (email, password) => {
  const response = await api.post('/auth/login', { email, password });
  if (response.data.token) {
    localStorage.setItem('user', JSON.stringify(response.data));
  }
  return response.data;
};

const signup = async (userData) => {
  const response = await api.post('/auth/signup', userData);
  return response.data;
};

const verify = async (email, code) => {
  const response = await api.post(`/auth/verify?email=${email}&code=${code}`);
  return response.data;
};

const resendCode = async (email) => {
  const response = await api.post(`/auth/resend-code?email=${email}`);
  return response.data;
};

const logout = () => {
  localStorage.removeItem('user');
};

const getUsers = async () => {
  const response = await api.get('/auth/users');
  return response.data;
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem('user'));
};

export default { login, signup, verify, resendCode, logout, getCurrentUser, getUsers };
