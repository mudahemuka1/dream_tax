import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import authService from '../api/authService';
import { motion } from 'framer-motion';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const user = await authService.login(email, password);
      // Route based on role
      switch (user.role) {
        case 'FARMER': navigate('/dash/farmer'); break;
        case 'AGRONOMIST': navigate('/dash/agronomist'); break;
        case 'ADMIN': navigate('/dash/admin'); break;
        case 'AGRO_DEALER': navigate('/dash/dealer'); break;
        default: navigate('/');
      }
    } catch (err) {
      if (err.response?.status === 403 && err.response?.data?.message?.includes('not verified')) {
        setError(<>
          Your account is not verified. 
          <span 
            onClick={() => navigate('/verify', { state: { email: email } })} 
            style={{ color: '#2e7d32', cursor: 'pointer', textDecoration: 'underline', marginLeft: '5px' }}
          >
            Verify Now
          </span>
        </>);
      } else {
        setError(err.response?.data?.message || 'Login failed. Please check credentials.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Navbar />
      <div className="auth-container" style={{ marginTop: '60px' }}>
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="glass-card"
        >
          <h1 className="heading">Smart Farming</h1>
          <p style={{ textAlign: 'center', marginBottom: '20px', color: '#666' }}>Welcome back, please login</p>
          
          {error && <p style={{ color: 'red', marginBottom: '15px', textAlign: 'center' }}>{error}</p>}
          
          <form onSubmit={handleLogin}>
            <label>Email Address</label>
            <input 
              className="input-field"
              type="email"
              placeholder="farmer@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            
            <label>Password</label>
            <input 
              className="input-field"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            
            <button className="btn-primary" type="submit" disabled={loading}>
              {loading ? 'Logging in...' : 'Login'}
            </button>
          </form>
          
          <p className="subtext">
            Don't have an account? <Link to="/signup" className="link">Sign Up</Link>
          </p>
        </motion.div>
      </div>
      <Footer />
    </>
  );
};

export default Login;
