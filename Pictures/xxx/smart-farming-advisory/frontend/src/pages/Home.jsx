import React from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { Leaf, Sprout, Tractor, HelpCircle, ShieldCheck } from 'lucide-react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const Home = () => {
  return (
    <>
      <Navbar />
      <div className="landing-container" style={{ marginTop: '80px' }}>
        {/* Hero Section */}
        <section className="hero-section" style={{ backgroundImage: `url('/hero-banner.png')` }}>
          <div className="hero-overlay">
            <motion.div 
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.8 }}
              className="hero-content glass"
            >
              <h1 className="hero-title">Rwanda's Smart Farming Advisor</h1>
              <p className="hero-subtitle">Empowering farmers with modern technology and expert local advice for better harvests.</p>
              <div className="cta-buttons">
                <Link to="/signup" className="btn-primary">Get Started</Link>
                <Link to="/login" className="btn-secondary">Login</Link>
              </div>
            </motion.div>
          </div>
        </section>

        {/* Features Section - Now Services */}
      <section id="services" className="features-grid">
        <motion.div whileHover={{ scale: 1.05 }} className="feature-card glass">
          <Tractor size={40} className="icon" />
          <h3>Crop Advice</h3>
          <p>Smart recommendations based on your soil type and current season.</p>
        </motion.div>
          
          <motion.div whileHover={{ scale: 1.05 }} className="feature-card glass">
            <Leaf size={40} className="icon" />
            <h3>Disease ID</h3>
            <p>Quickly identify pests and diseases to save your precious crops.</p>
          </motion.div>

          <motion.div whileHover={{ scale: 1.05 }} className="feature-card glass">
            <HelpCircle size={40} className="icon" />
            <h3>Expert Q&A</h3>
            <p>Ask our local agronomists any questions about your farm.</p>
          </motion.div>

          <motion.div whileHover={{ scale: 1.05 }} className="feature-card glass">
            <ShieldCheck size={40} className="icon" />
            <h3>Secure & Trusted</h3>
            <p>Access your personalized dashboard from any device safely.</p>
          </motion.div>
        </section>

      {/* New About Us Section */}
      <section id="about" className="glass-card" style={{ margin: '40px 10%', maxWidth: 'none' }}>
        <h2 style={{ color: 'var(--primary)', textAlign: 'center', marginBottom: '20px' }}>About Us</h2>
        <p style={{ textAlign: 'center', color: '#555', lineHeight: '1.6' }}>
          Smart Farming Advisory is a Rwandan initiative dedicated to digitizing agriculture. 
          We use technology to bridge the gap between farmers and agronomists, ensuring every 
          field in Rwanda reaches its full potential through expert advice and data-driven crop management.
        </p>
      </section>
      </div>
      <Footer />
    </>
  );
};

export default Home;
