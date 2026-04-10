import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Users, LayoutDashboard, Settings, LogOut, PlusCircle } from 'lucide-react';
import authService from '../api/authService';
import api from '../api/api';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const AdminDashboard = () => {
  const [activeTab, setActiveTab] = useState('overview');
  const [subView, setSubView] = useState('default'); // 'default', 'addUser', 'listUsers', 'addCrop', 'listCrops'
  
  // Registration Feature State
  const [newUser, setNewUser] = useState({
    email: '', password: '', role: 'FARMER', fullName: '', location: '', companyName: '', specialization: ''
  });
  const [createMsg, setCreateMsg] = useState({ text: '', type: '' });
  const [newCrop, setNewCrop] = useState({
    name: '', suitableSoilType: '', suitableSeason: '', growingDurationDays: '', description: ''
  });
  const [cropsList, setCropsList] = useState([]);
  const [usersList, setUsersList] = useState([]);

  const navigate = useNavigate();
  const user = authService.getCurrentUser();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  const switchTab = (tab) => {
    setActiveTab(tab);
    setSubView('default');
    setCreateMsg({ text: '', type: '' });
  };

  const fetchUsers = async () => {
    try {
      const data = await authService.getUsers();
      setUsersList(data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchCrops = async () => {
    try {
      const res = await api.get('/crops');
      setCropsList(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (subView === 'listUsers') fetchUsers();
    if (subView === 'listCrops') fetchCrops();
  }, [subView]);

  const handleCreateCrop = async () => {
    try {
      const payload = { ...newCrop };
      await api.post('/crops', payload);
      setCreateMsg({ text: 'Crop rule added successfully!', type: 'success' });
      setTimeout(() => {
        setSubView('default');
        setCreateMsg({ text: '', type: '' });
        setNewCrop({ name: '', suitableSoilType: '', suitableSeason: '', growingDurationDays: '', description: '' });
      }, 2000);
    } catch (err) {
      console.error('Add Crop Error:', err);
      const errorDetail = err.response?.data?.message || err.message || 'Unknown error';
      setCreateMsg({ text: `Failed to add crop rule: ${errorDetail}`, type: 'error' });
    }
  };

  const handleCreateUser = async () => {
    try {
      await authService.signup(newUser);
      setCreateMsg({ text: 'User successfully created!', type: 'success' });
      setTimeout(() => {
        setSubView('default');
        setCreateMsg({ text: '', type: '' });
      }, 2000);
    } catch (err) {
      setCreateMsg({ text: err.response?.data?.message || 'Failed to create user.', type: 'error' });
    }
  };

  return (
    <>
      <Navbar />
      <div className="dashboard-container" style={{ marginTop: '70px' }}>
        {/* Sidebar */}
        <nav className="sidebar">
          <div className="logo-section">
            <h2 className="logo-text">SF Admin</h2>
          </div>
          
          <div className="nav-items">
            <button className={`nav-btn ${activeTab === 'overview' ? 'active' : ''}`} onClick={() => switchTab('overview')}>
              <LayoutDashboard size={20} /> Overview
            </button>
            <button className={`nav-btn ${activeTab === 'users' ? 'active' : ''}`} onClick={() => switchTab('users')}>
              <Users size={20} /> Manage Users
            </button>
            <button className={`nav-btn ${activeTab === 'data' ? 'active' : ''}`} onClick={() => switchTab('data')}>
              <PlusCircle size={20} /> Manage Crops/Diseases
            </button>
            <button className={`nav-btn ${activeTab === 'settings' ? 'active' : ''}`} onClick={() => switchTab('settings')}>
              <Settings size={20} /> System Settings
            </button>
          </div>

          <button className="logout-btn" onClick={handleLogout}>
            <LogOut size={20} /> Logout
          </button>
        </nav>

        {/* Main Content */}
        <main className="main-content">
          <header className="top-bar glass">
            <h1>Admin Command Center</h1>
            <div className="user-profile">
              <span>Welcome, {user?.name || user?.email}</span>
            </div>
          </header>

          <section className="content-grid">
            {activeTab === 'overview' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2>System Overview</h2>
                <div className="stats-row" style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
                  <div className="stat-card" style={{ background: '#e8f5e9', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#2e7d32', fontSize: '2rem' }}>1,240</h3><p>Total Farmers</p>
                  </div>
                  <div className="stat-card" style={{ background: '#e3f2fd', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#1565c0', fontSize: '2rem' }}>15</h3><p>Active Agronomists</p>
                  </div>
                  <div className="stat-card" style={{ background: '#fff3e0', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#ef6c00', fontSize: '2rem' }}>82</h3><p>Common Diseases</p>
                  </div>
                  <div className="stat-card" style={{ background: '#f3e5f5', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#7b1fa2', fontSize: '2rem' }}>450</h3><p>Q&A Threads</p>
                  </div>
                </div>
              </motion.div>
            )}

            {activeTab === 'users' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                {subView === 'default' ? (
                  <>
                    <h2>Manage Users</h2>
                    <p>Register new platform users, deactivate accounts, and set roles.</p>
                    <div style={{ marginTop: '20px' }}>
                      <button className="btn-primary" style={{ width: 'auto' }} onClick={() => setSubView('addUser')}>+ Add New User</button>
                      <button className="btn-secondary" style={{ width: 'auto', marginLeft: '10px', background:'#fbc02d', color:'#000', padding:'12px 24px', borderRadius:'12px', border:'none', cursor:'pointer' }} onClick={() => setSubView('listUsers')}>View All Users</button>
                    </div>
                  </>
                ) : subView === 'addUser' ? (
                  <>
                    <h2>Add New User Form</h2>
                    {createMsg.text && (
                      <p style={{ color: createMsg.type === 'error' ? 'red' : 'green', margin: '10px 0' }}>
                        {createMsg.text}
                      </p>
                    )}
                    <form style={{ marginTop: '20px', display: 'flex', flexDirection: 'column', gap: '15px' }} onSubmit={(e) => { e.preventDefault(); handleCreateUser(); }}>
                      <input className="input-field" type="email" style={{ margin: 0 }} placeholder="Email Address" required onChange={e => setNewUser({...newUser, email: e.target.value})} />
                      <input className="input-field" type="password" style={{ margin: 0 }} placeholder="Password" required onChange={e => setNewUser({...newUser, password: e.target.value})} />
                      
                      <select className="input-field" style={{ margin: 0 }} value={newUser.role} onChange={e => setNewUser({...newUser, role: e.target.value})}>
                        <option value="FARMER">Farmer</option>
                        <option value="AGRONOMIST">Agronomist</option>
                        <option value="AGRO_DEALER">Agro Dealer</option>
                        <option value="ADMIN">Admin</option>
                      </select>
                      
                      {['FARMER', 'AGRONOMIST'].includes(newUser.role) && (
                        <input className="input-field" style={{ margin: 0 }} placeholder="Full Name" required onChange={e => setNewUser({...newUser, fullName: e.target.value})} />
                      )}
                      
                      {['FARMER', 'AGRO_DEALER'].includes(newUser.role) && (
                        <input className="input-field" style={{ margin: 0 }} placeholder="Location" required onChange={e => setNewUser({...newUser, location: e.target.value})} />
                      )}

                      {newUser.role === 'AGRONOMIST' && (
                        <input className="input-field" style={{ margin: 0 }} placeholder="Specialization" required onChange={e => setNewUser({...newUser, specialization: e.target.value})} />
                      )}

                      {newUser.role === 'AGRO_DEALER' && (
                        <input className="input-field" style={{ margin: 0 }} placeholder="Company Name" required onChange={e => setNewUser({...newUser, companyName: e.target.value})} />
                      )}

                      <div style={{ display: 'flex', gap: '10px' }}>
                        <button type="submit" className="btn-primary" style={{ width: '150px' }}>Save User</button>
                        <button type="button" className="btn-primary" style={{ width: '150px', background: '#ccc' }} onClick={() => setSubView('default')}>Cancel</button>
                      </div>
                    </form>
                  </>
                ) : subView === 'listUsers' ? (
                  <>
                    <h2>User Directory</h2>
                    <table className="data-table">
                      <thead>
                        <tr><th>ID</th><th>Email Address</th><th>Role</th><th>Joined</th></tr>
                      </thead>
                      <tbody>
                        {usersList.length > 0 ? usersList.map(u => (
                          <tr key={u.id}>
                            <td>#{u.id}</td>
                            <td>{u.email}</td>
                            <td><span style={{ padding: '4px 8px', background: '#e3f2fd', color: '#1565c0', borderRadius: '4px', fontSize: '0.8rem', fontWeight: 'bold' }}>{u.role}</span></td>
                            <td>{new Date(u.createdAt).toLocaleDateString()}</td>
                          </tr>
                        )) : <tr><td colSpan="4">Loading users...</td></tr>}
                      </tbody>
                    </table>
                    <button className="btn-primary" style={{ width: 'auto', marginTop: '20px' }} onClick={() => setSubView('default')}>Back to Manage Users</button>
                  </>
                ) : null}
              </motion.div>
            )}

            {activeTab === 'data' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                {subView === 'default' ? (
                  <>
                    <h2>Crop & Disease Management</h2>
                    <p>Add new crop guides and update disease knowledge bases.</p>
                    <div style={{ marginTop: '20px', display:'flex', gap:'10px' }}>
                      <button className="btn-primary" style={{ width: 'auto' }} onClick={() => setSubView('addCrop')}>+ New Crop Base</button>
                      <button className="btn-secondary" style={{ width: 'auto', background:'#fbc02d', color:'#000', padding:'12px 24px', borderRadius:'12px', border:'none', cursor:'pointer' }} onClick={() => setSubView('listCrops')}>View Existing</button>
                    </div>
                  </>
                ) : subView === 'addCrop' ? (
                  <>
                    <h2>Add New Crop Rule</h2>
                    {createMsg.text && (
                      <p style={{ color: createMsg.type === 'error' ? 'red' : 'green', margin: '10px 0' }}>{createMsg.text}</p>
                    )}
                    <form style={{ marginTop: '20px', display: 'flex', flexDirection: 'column', gap: '15px' }} onSubmit={(e) => { e.preventDefault(); handleCreateCrop(); }}>
                      <input className="input-field" style={{ margin: 0 }} placeholder="Crop Name" required onChange={e => setNewCrop({...newCrop, name: e.target.value})} value={newCrop.name} />
                      <input className="input-field" style={{ margin: 0 }} placeholder="Optimal Soil Type" required onChange={e => setNewCrop({...newCrop, suitableSoilType: e.target.value})} value={newCrop.suitableSoilType} />
                      <input className="input-field" style={{ margin: 0 }} placeholder="Suitable Season" required onChange={e => setNewCrop({...newCrop, suitableSeason: e.target.value})} value={newCrop.suitableSeason} />
                      <input className="input-field" type="text" style={{ margin: 0 }} placeholder="Growing Days (e.g. 90-120)" required onChange={e => setNewCrop({...newCrop, growingDurationDays: e.target.value})} value={newCrop.growingDurationDays} />
                      <textarea className="input-field" style={{ margin: 0, resize: 'vertical' }} rows={3} placeholder="Crop Description" required onChange={e => setNewCrop({...newCrop, description: e.target.value})} value={newCrop.description} />
                      <div style={{ display: 'flex', gap: '10px' }}>
                        <button type="submit" className="btn-primary" style={{ width: '150px' }}>Save Rule</button>
                        <button type="button" className="btn-primary" style={{ width: '150px', background: '#ccc' }} onClick={() => setSubView('default')}>Cancel</button>
                      </div>
                    </form>
                  </>
                ) : subView === 'listCrops' ? (
                  <>
                    <h2>Existing Crops Dictionary</h2>
                    <table className="data-table">
                      <thead>
                        <tr><th>Crop Name</th><th>Soil</th><th>Season</th><th>Days</th></tr>
                      </thead>
                      <tbody>
                        {cropsList.length > 0 ? cropsList.map(c => (
                          <tr key={c.id}>
                            <td style={{ fontWeight: 'bold' }}>{c.name}</td>
                            <td>{c.suitableSoilType}</td>
                            <td>{c.suitableSeason}</td>
                            <td>{c.growingDurationDays}</td>
                          </tr>
                        )) : <tr><td colSpan="4">No crops found.</td></tr>}
                      </tbody>
                    </table>
                    <button className="btn-primary" style={{ width: 'auto', marginTop: '20px' }} onClick={() => setSubView('default')}>Back to Dashboard</button>
                  </>
                ) : null}
              </motion.div>
            )}

            {activeTab === 'settings' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2>System Settings</h2>
                <p>Modify global application features and backend configurations.</p>
              </motion.div>
            )}
          </section>
        </main>
      </div>
      <Footer />
    </>
  );
};

export default AdminDashboard;
