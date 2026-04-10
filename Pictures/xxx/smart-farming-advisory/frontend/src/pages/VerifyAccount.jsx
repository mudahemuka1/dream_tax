import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import authService from '../api/authService';
import { motion } from 'framer-motion';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const VerifyAccount = () => {
    const [code, setCode] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);
    
    const navigate = useNavigate();
    const location = useLocation();
    const email = location.state?.email;

    const handleVerify = async (e) => {
        e.preventDefault();
        if (!email) {
            setError('Missing email. Please register again.');
            return;
        }
        
        setLoading(true);
        setError('');
        try {
            await authService.verify(email, code);
            setSuccess('Account verified successfully! Redirecting to login...');
            setTimeout(() => navigate('/login'), 3000);
        } catch (err) {
            setError(err.response?.data?.message || 'Verification failed. Try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleResend = async () => {
        if (!email) return;
        setError('');
        setSuccess('');
        try {
            await authService.resendCode(email);
            setSuccess('A new code has been sent to your email.');
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to resend code.');
        }
    };

    return (
        <>
            <Navbar />
            <div className="auth-container" style={{ marginTop: '80px' }}>
                <motion.div 
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    className="glass-card"
                    style={{ maxWidth: '400px' }}
                >
                    <h1 className="heading">Verify Email</h1>
                    <p style={{ textAlign: 'center', marginBottom: '20px', color: '#666' }}>
                        We sent a 6-digit code to <b>{email}</b>
                    </p>

                    {error && <p style={{ color: 'red', marginBottom: '15px', textAlign: 'center' }}>{error}</p>}
                    {success && <p style={{ color: '#2e7d32', marginBottom: '15px', textAlign: 'center', fontWeight: 'bold' }}>{success}</p>}

                    <form onSubmit={handleVerify}>
                        <label>Enter Verification Code</label>
                        <input 
                            className="input-field"
                            type="text"
                            placeholder="6-digit code"
                            maxLength="6"
                            value={code}
                            onChange={(e) => setCode(e.target.value)}
                            required
                            style={{ textAlign: 'center', fontSize: '1.5rem', letterSpacing: '5px' }}
                        />

                        <button className="btn-primary" type="submit" disabled={loading}>
                            {loading ? 'Verifying...' : 'Verify Account'}
                        </button>
                    </form>

                    <p className="subtext" style={{ marginTop: '20px' }}>
                        Didn't receive a code? <span onClick={handleResend} style={{ color: '#2e7d32', cursor: 'pointer', fontWeight: 'bold' }}>Resend Code</span>
                    </p>
                </motion.div>
            </div>
            <Footer />
        </>
    );
};

export default VerifyAccount;
