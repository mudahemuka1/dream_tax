import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Package, Truck, LayoutDashboard, LogOut } from 'lucide-react';
import authService from '../api/authService';
import api from '../api/api';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const AgroDealerDashboard = () => {
  const [activeTab, setActiveTab] = useState('inventory');
  const [subView, setSubView] = useState('default'); // 'default', 'listOrders', 'editStock'
  const [fertilizers, setFertilizers] = useState([]);
  const [editingFert, setEditingFert] = useState(null);
  const [updateMsg, setUpdateMsg] = useState({ text: '', type: '' });
  
  const navigate = useNavigate();
  const user = authService.getCurrentUser();

  useEffect(() => {
    fetchFertilizers();
  }, []);

  const fetchFertilizers = async () => {
    try {
      const res = await api.get('/fertilizers');
      setFertilizers(res.data);
    } catch (err) {
      console.error('Failed to fetch fertilizers');
    }
  };

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  const switchTab = (tab) => {
    setActiveTab(tab);
    setSubView('default');
    setUpdateMsg({ text: '', type: '' });
  };

  const startEdit = (fert) => {
    setEditingFert({ ...fert, newStock: fert.stockBags || 0, newPrice: fert.pricePerBag || 0 });
    setSubView('editStock');
  };

  const handleUpdateStock = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/fertilizers/${editingFert.id}?stock=${editingFert.newStock}&price=${editingFert.newPrice}`);
      setUpdateMsg({ text: 'Stock updated successfully!', type: 'success' });
      fetchFertilizers();
      setTimeout(() => {
        setSubView('default');
        setUpdateMsg({ text: '', type: '' });
      }, 2000);
    } catch (err) {
      setUpdateMsg({ text: 'Update failed. Please try again.', type: 'error' });
    }
  };

  return (
    <>
      <Navbar />
      <div className="dashboard-container" style={{ marginTop: '70px' }}>
        {/* Sidebar */}
        <nav className="sidebar">
          <div className="logo-section">
            <h2 className="logo-text">AgroDealer</h2>
          </div>
          
          <div className="nav-items">
            <button className={`nav-btn ${activeTab === 'overview' ? 'active' : ''}`} onClick={() => switchTab('overview')}>
              <LayoutDashboard size={20} /> Dashboard
            </button>
            <button className={`nav-btn ${activeTab === 'inventory' ? 'active' : ''}`} onClick={() => switchTab('inventory')}>
              <Package size={20} /> Manage Stock
            </button>
            <button className={`nav-btn ${activeTab === 'delivery' ? 'active' : ''}`} onClick={() => switchTab('delivery')}>
              <Truck size={20} /> Deliveries
            </button>
          </div>

          <button className="logout-btn" onClick={handleLogout}>
            <LogOut size={20} /> Logout
          </button>
        </nav>

        {/* Main Content */}
        <main className="main-content">
          <header className="top-bar glass">
            <h1>Stock Management</h1>
            <div className="user-profile">
              <span>Welcome, {user?.name || user?.email}</span>
            </div>
          </header>

          <section className="content-grid">
            {activeTab === 'overview' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2>Dealer Overview</h2>
                <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
                  <div className="stat-card" style={{ background: '#e8f5e9', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#2e7d32', fontSize: '2rem' }}>1,770</h3><p>Total Bags in Stock</p>
                  </div>
                  <div className="stat-card" style={{ background: '#fff3e0', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#ef6c00', fontSize: '2rem' }}>14</h3><p>Active Deliveries</p>
                  </div>
                </div>
              </motion.div>
            )}

            {activeTab === 'inventory' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                {subView === 'default' ? (
                  <>
                    <h2 style={{ marginBottom: '20px' }}>Current Inventory</h2>
                    <table className="data-table">
                      <thead>
                        <tr><th>ID</th><th>Fertilizer</th><th>Stock (Bags)</th><th>Price/Bag</th><th>Action</th></tr>
                      </thead>
                      <tbody>
                        {fertilizers.length > 0 ? fertilizers.map(f => (
                          <tr key={f.id}>
                            <td>#{f.id}</td>
                            <td style={{ fontWeight: 'bold' }}>{f.name}</td>
                            <td style={{ color: (f.stockBags || 0) < 50 ? 'red' : 'inherit' }}>{f.stockBags || 0}</td>
                            <td>{f.pricePerBag?.toLocaleString() || '0'} RWF</td>
                            <td><button className="btn-small" onClick={() => startEdit(f)}>Update</button></td>
                          </tr>
                        )) : <tr><td colSpan="5">No inventory assigned. Add fertilizers in Admin first.</td></tr>}
                      </tbody>
                    </table>
                  </>
                ) : subView === 'editStock' && (
                  <>
                    <h2>Update Stock: {editingFert?.name}</h2>
                    {updateMsg.text && (
                      <p style={{ color: updateMsg.type === 'error' ? 'red' : 'green', margin: '15px 0', fontWeight: 'bold' }}>{updateMsg.text}</p>
                    )}
                    <form onSubmit={handleUpdateStock} style={{ marginTop: '20px', display: 'flex', flexDirection: 'column', gap: '15px', maxWidth: '400px' }}>
                      <div className="form-group">
                        <label>Stock Count (Bags)</label>
                        <input 
                          type="number" 
                          className="input-field" 
                          value={editingFert.newStock} 
                          onChange={e => setEditingFert({...editingFert, newStock: e.target.value})}
                          required 
                        />
                      </div>
                      <div className="form-group">
                        <label>Price per Bag (RWF)</label>
                        <input 
                          type="number" 
                          className="input-field" 
                          value={editingFert.newPrice} 
                          onChange={e => setEditingFert({...editingFert, newPrice: e.target.value})}
                          required 
                        />
                      </div>
                      <div style={{ display: 'flex', gap: '10px' }}>
                        <button type="submit" className="btn-primary">Save Changes</button>
                        <button type="button" className="btn-secondary" style={{ background: '#ccc' }} onClick={() => setSubView('default')}>Cancel</button>
                      </div>
                    </form>
                  </>
                )}
              </motion.div>
            )}

            {activeTab === 'delivery' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                {subView === 'default' ? (
                  <>
                    <h2>Delivery Operations</h2>
                    <p>Manage pending farmer orders and track dispatch statuses.</p>
                    <div style={{ marginTop: '20px' }}>
                      <button className="btn-primary" style={{ width: 'auto' }} onClick={() => setSubView('listOrders')}>View Pending Orders</button>
                    </div>
                  </>
                ) : subView === 'listOrders' ? (
                  <>
                    <h2>Pending Orders</h2>
                    <p>No new delivery requests found at this time.</p>
                    <button className="btn-primary" style={{ width: 'auto', marginTop: '20px' }} onClick={() => setSubView('default')}>Back</button>
                  </>
                ) : null}
              </motion.div>
            )}
          </section>
        </main>
      </div>
      <Footer />
    </>
  );
};

export default AgroDealerDashboard;
