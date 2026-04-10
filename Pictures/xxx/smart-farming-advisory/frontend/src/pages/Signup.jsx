import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import authService from '../api/authService';
import { motion } from 'framer-motion';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const Signup = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    role: 'FARMER',
    fullName: '',
    location: '',
    companyName: '',
    specialization: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      await authService.signup(formData);
      setSuccess(true);
      setTimeout(() => navigate('/verify', { state: { email: formData.email } }), 2000);
    } catch (err) {
      console.error('Signup Error:', err);
      setError(err.response?.data?.message || err.message || 'Signup failed.');
    }
  };

  return (
    <>
      <Navbar />
      <div className="auth-container" style={{ marginTop: '80px' }}>
        <motion.div 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          className="glass-card"
        >
          <h1 className="heading">Join Us</h1>
          
          {error && <p style={{ color: 'red', marginBottom: '15px' }}>{error}</p>}
          {success && <p style={{ color: 'green', marginBottom: '15px' }}>Signup successful! Redirecting...</p>}

          <form onSubmit={handleSignup}>
            <input className="input-field" type="email" placeholder="Email Address" required 
              onChange={e => setFormData({...formData, email: e.target.value})} />
            
            <input className="input-field" type="password" placeholder="Password" required 
              onChange={e => setFormData({...formData, password: e.target.value})} />
            
            <select className="input-field" value={formData.role} 
              onChange={e => setFormData({...formData, role: e.target.value})}>
              <option value="FARMER">Farmer</option>
              <option value="AGRONOMIST">Agronomist</option>
              <option value="AGRO_DEALER">Agro Dealer</option>
              <option value="ADMIN">Admin</option>
            </select>

            {(formData.role === 'FARMER' || formData.role === 'AGRONOMIST') && (
              <input className="input-field" placeholder="Full Name" required 
                onChange={e => setFormData({...formData, fullName: e.target.value})} />
            )}

            {(formData.role === 'FARMER' || formData.role === 'AGRO_DEALER') && (
              <input className="input-field" placeholder="Location" required 
                onChange={e => setFormData({...formData, location: e.target.value})} />
            )}

            {formData.role === 'AGRONOMIST' && (
              <input className="input-field" placeholder="Specialization" required 
                onChange={e => setFormData({...formData, specialization: e.target.value})} />
            )}

            {formData.role === 'AGRO_DEALER' && (
              <input className="input-field" placeholder="Company Name" required 
                onChange={e => setFormData({...formData, companyName: e.target.value})} />
            )}

            <button className="btn-primary" type="submit">Sign Up</button>
          </form>
          
          <p className="subtext">
            Already registered? <Link to="/login" className="link">Login</Link>
          </p>
        </motion.div>
      </div>
      <Footer />
    </>
  );
};

export default Signup;
