import React from 'react';
import { Mail, Phone, MapPin, ExternalLink } from 'lucide-react';

const Footer = () => {
  return (
    <footer className="main-footer">
      <div className="footer-content">
        <div className="footer-brand">
          <h4 style={{ color: '#fff' }}>SMART FARMING ADVISORY</h4>
          <p>Innovating Rwandan Agriculture through AI-driven insights and local expertise.</p>
        </div>
        
        <div className="footer-links">
          <h5 style={{ color: '#fff' }}>Links z'ingenzi</h5>
          <a href="/terms"><ExternalLink size={14} /> Terms & Conditions</a>
          <a href="/privacy"><ExternalLink size={14} /> Privacy Policy</a>
          <a href="/help"><ExternalLink size={14} /> Help & FAQ</a>
        </div>

        <div className="footer-contact">
          <h5 style={{ color: '#fff' }}>Contact Us</h5>
          <p style={{ display: 'flex', alignItems: 'center', gap: '5px' }}><Mail size={16} /> smartfarming@gmail.com</p>
          <p style={{ display: 'flex', alignItems: 'center', gap: '5px' }}><Phone size={16} /> 0782299538</p>
          <p style={{ display: 'flex', alignItems: 'center', gap: '5px' }}><MapPin size={16} /> Kigali, Rwanda</p>
        </div>
      </div>
      
      <div className="footer-bottom">
        <p>Copyright &copy; {new Date().getFullYear()} Fidela Mudahemuka. All Rights Reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
