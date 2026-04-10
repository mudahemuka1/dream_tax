import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { LogOut, Home, Leaf, ShieldAlert, MessageCircle, User as UserIcon } from 'lucide-react';
import authService from '../api/authService';

const Sidebar = ({ user, activeTab, onTabChange }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  return (
    <div className="sidebar">
      <div style={{ marginBottom: '40px', textAlign: 'center' }}>
        <h2 style={{ fontSize: '1.5rem', fontWeight: 700 }}>Smart Farming</h2>
        <p style={{ fontSize: '0.8rem', opacity: 0.8 }}>{user.role} Portal</p>
      </div>

      <nav style={{ flex: 1 }}>
        <ul style={{ listStyle: 'none' }}>
          <li style={{ marginBottom: '15px' }}>
            <NavLink to={`/dash/${user.role.toLowerCase()}`} className="nav-link" style={({isActive}) => ({
              display: 'flex', alignItems: 'center', gap: '10px', color: 'white', textDecoration: 'none',
              padding: '12px', borderRadius: '8px', background: isActive ? 'rgba(255,255,255,0.1)' : 'transparent'
            })}>
              <Home size={20} /> Dashboard
            </NavLink>
          </li>
          
          {user.role === 'FARMER' && (
            <>
              <li style={{ marginBottom: '15px' }}>
                <NavLink to="#" onClick={(e) => { e.preventDefault(); onTabChange('advice'); }} className="nav-link" style={({isActive}) => ({
                  display: 'flex', alignItems: 'center', gap: '10px', color: 'white', textDecoration: 'none',
                  padding: '12px', borderRadius: '8px', background: activeTab === 'advice' ? 'rgba(255,255,255,0.1)' : 'transparent'
                })}>
                  <Leaf size={20} /> Crop Advice
                </NavLink>
              </li>
              <li style={{ marginBottom: '15px' }}>
                <NavLink to="#" onClick={(e) => { e.preventDefault(); onTabChange('forum'); }} className="nav-link" style={({isActive}) => ({
                  display: 'flex', alignItems: 'center', gap: '10px', color: 'white', textDecoration: 'none',
                  padding: '12px', borderRadius: '8px', background: activeTab === 'forum' ? 'rgba(255,255,255,0.1)' : 'transparent'
                })}>
                  <MessageCircle size={20} /> Q&A Forum
                </NavLink>
              </li>
            </>
          )}

          {user.role === 'AGRONOMIST' && (
            <li style={{ marginBottom: '15px' }}>
              <NavLink to="#" onClick={(e) => { e.preventDefault(); alert('Pending Questions functionality coming soon!'); }} className="nav-link" style={{ display: 'flex', alignItems: 'center', gap: '10px', color: 'white', textDecoration: 'none' }}>
                <MessageCircle size={20} /> Pending Questions
              </NavLink>
            </li>
          )}
        </ul>
      </nav>

      <button onClick={handleLogout} style={{
        marginTop: 'auto', background: 'transparent', border: 'none', color: 'white',
        display: 'flex', alignItems: 'center', gap: '10px', cursor: 'pointer', padding: '12px'
      }}>
        <LogOut size={20} /> Logout
      </button>
    </div>
  );
};

export default Sidebar;
