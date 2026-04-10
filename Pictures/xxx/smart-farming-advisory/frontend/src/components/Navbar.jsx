import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Leaf, LogOut, User, Star } from 'lucide-react';
import authService from '../api/authService';

const Navbar = () => {
  const navigate = useNavigate();
  const currentUser = authService.getCurrentUser();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  const toggleTheme = () => {
    document.body.classList.toggle('dark-mode');
  };

  return (
    <nav className="navbar dash-header">
      <div className="navbar-brand">
        <Link to="/" className="logo-link">
          <Leaf className="logo-icon" color="#4caf50" />
          <span className="logo-text">SF ADVISORY</span>
        </Link>
      </div>

      <div className="navbar-menu-horizontal" style={{ display: 'flex', gap: '30px' }}>
        <Link to="/" className="nav-item">Home</Link>
        <a href="/#about" className="nav-item">About Us</a>
        <a href="/#services" className="nav-item">Services</a>
        {currentUser && <Link to={`/dash/${currentUser.role.toLowerCase()}`} className="nav-item">Dashboard</Link>}
      </div>

      <div className="navbar-links" style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
        <button 
          onClick={toggleTheme} 
          style={{ background: 'transparent', border: 'none', cursor: 'pointer', display: 'flex', alignItems: 'center' }}
          title="Toggle Dark Mode"
        >
          <Star size={22} color="#fbc02d" fill="#fbc02d" />
        </button>

        {currentUser ? (
          <div className="user-nav-section" style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
            <div className="user-info-chip" style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#fff', background: '#333', padding: '5px 12px', borderRadius: '20px' }}>
              <User size={16} />
              <span style={{ fontSize: '0.9rem' }}>{currentUser.username}</span>
            </div>
            <button onClick={handleLogout} className="btn-logout" style={{ color: '#ff5252', borderColor: '#ff5252' }}>
               Logout
            </button>
          </div>
        ) : (
          <>
            <Link to="/login" className="nav-item">Login</Link>
            <Link to="/signup" className="nav-register">Join Now</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
