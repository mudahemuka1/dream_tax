import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../components/Sidebar';
import authService from '../api/authService';
import api from '../api/api';
import { motion } from 'framer-motion';
import { Leaf, Search, HelpCircle, LayoutDashboard, LogOut } from 'lucide-react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const FarmerDashboard = () => {
  const user = authService.getCurrentUser();
  const [activeTab, setActiveTab] = useState('overview');
  const [crops, setCrops] = useState([]);
  const [noCropsFound, setNoCropsFound] = useState(false);
  const [recommendationParams, setRecommendationParams] = useState({ soil: 'Loam', season: 'Summer' });
  const [questions, setQuestions] = useState([]);
  const [forumSubView, setForumSubView] = useState('list'); // 'list', 'ask', or 'answers'
  const [selectedQuestion, setSelectedQuestion] = useState(null);
  const [newQuestion, setNewQuestion] = useState({ title: '', content: '' });
  const navigate = useNavigate();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  useEffect(() => {
    fetchFarmerData();
  }, []);

  const fetchFarmerData = async () => {
    if (!user) return;
    try {
      const qRes = await api.get(`/questions/farmer/${user.id}`);
      setQuestions(qRes.data);
    } catch (err) {
      console.error(err);
    }
  };

  const submitQuestion = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        title: newQuestion.title,
        content: newQuestion.content,
        farmerId: user.id
      };
      const response = await api.post('/questions', payload);
      if (response.status === 200) {
        setForumSubView('list');
        setNewQuestion({ title: '', content: '' });
        fetchFarmerData();
        alert('Question submitted successfully!');
      }
    } catch (err) {
      alert('Failed to submit question. Only Farmers can ask questions.');
      console.error(err);
    }
  };

  const getRecommendations = async () => {
    setNoCropsFound(false);
    try {
      const res = await api.get('/crops/recommend', {
        params: {
          soil: recommendationParams.soil,
          season: recommendationParams.season
        }
      });
      setCrops(res.data);
      if (res.data.length === 0) setNoCropsFound(true);
    } catch (err) {
      console.error('Rec error:', err);
      alert("Failed to get recommendations. Check console for details.");
    }
  };

  return (
    <>
      <Navbar />
      <div className="dashboard-container" style={{ marginTop: '70px' }}>
        {/* Sidebar */}
        <Sidebar 
          user={user} 
          activeTab={activeTab} 
          onTabChange={setActiveTab} 
        />

        {/* Main Content */}
        <main className="main-content">
          <header className="top-bar glass">
            <h1>Farmer Portal</h1>
            <div className="user-profile">
              <span>Welcome, {user?.name || user?.email}</span>
            </div>
          </header>

          <section className="content-grid">
            {activeTab === 'overview' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2>System Overview</h2>
                <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
                  <div className="stat-card" style={{ background: '#e8f5e9', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#2e7d32', fontSize: '2rem' }}>{questions.length}</h3><p>Questions Asked</p>
                  </div>
                  <div className="stat-card" style={{ background: '#fff3e0', padding: '20px', borderRadius: '10px', flex: 1, textAlign: 'center' }}>
                    <h3 style={{ color: '#ef6c00', fontSize: '2rem' }}>{crops.length}</h3><p>Active Recommendations</p>
                  </div>
                </div>
              </motion.div>
            )}

            {activeTab === 'advice' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2 style={{ marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '10px' }}>
                  <Leaf color="#2e7d32" /> Smart Crop Recommendation
                </h2>
                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '15px', alignItems: 'flex-end', background: 'rgba(0,0,0,0.03)', padding: '20px', borderRadius: '15px' }}>
                  <div style={{ flex: 1, minWidth: '150px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', fontWeight: '600' }}>Soil Type</label>
                    <select className="input-field" style={{ margin: 0 }} onChange={e => setRecommendationParams({...recommendationParams, soil: e.target.value})}>
                      <option value="Loam">Loam</option>
                      <option value="Clay">Clay</option>
                      <option value="Sand">Sand</option>
                      <option value="Silt">Silt</option>
                    </select>
                  </div>
                  <div style={{ flex: 1, minWidth: '150px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', fontWeight: '600' }}>Season</label>
                    <select className="input-field" style={{ margin: 0 }} onChange={e => setRecommendationParams({...recommendationParams, season: e.target.value})}>
                      <option value="Summer">Summer Season</option>
                      <option value="Winter">Winter Season</option>
                      <option value="Rainy">Rainy Season</option>
                      <option value="Spring">Spring Season</option>
                    </select>
                  </div>
                  <button className="btn-primary" onClick={getRecommendations} style={{ width: 'auto', padding: '13px 30px' }}>Get Expert Advice</button>
                </div>

              {noCropsFound && (
                <div style={{ marginTop: '20px', padding: '15px', background: '#ffebee', color: '#c62828', borderRadius: '8px' }}>
                  No optimal crops found for {recommendationParams.soil} soil in {recommendationParams.season} season. Please try different parameters or register new crops in the Admin portal.
                </div>
              )}
              {crops.length > 0 && (
                <div className="card-grid" style={{ marginTop: '20px' }}>
                  {crops.map(crop => (
                    <div key={crop.id} className="data-card">
                      <h3 style={{ color: '#2e7d32' }}>{crop.name}</h3>
                      <p style={{ margin: '10px 0' }}>{crop.description}</p>
                      <p><strong>Growing Days:</strong> {crop.growingDurationDays}</p>
                    </div>
                  ))}
                </div>
              )}
              </motion.div>
            )}

            {activeTab === 'forum' && (
              <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }} className="glass-card full-width">
                <h2 style={{ marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '10px' }}>
                  <HelpCircle color="#2e7d32" /> Q&A Forum
                </h2>
                
                {forumSubView === 'list' ? (
                  <>
                    <div style={{ marginBottom: '20px' }}>
                      <button className="btn-primary" style={{ width: 'auto' }} onClick={() => setForumSubView('ask')}>+ Ask a Question</button>
                    </div>
                    <h3>My Recent Questions</h3>
                    <div className="card-grid" style={{ marginTop: '15px' }}>
                      {questions.length > 0 ? questions.map(q => (
                        <div key={q.id} className="data-card">
                          <h4 style={{ color: '#2e7d32' }}>{q.title}</h4>
                          <p style={{ margin: '10px 0', fontSize: '1rem' }}>{q.content}</p>
                          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '15px' }}>
                            <span style={{ 
                              padding: '4px 10px', borderRadius: '15px', fontSize: '0.8rem',
                              background: q.isResolved ? '#e8f5e9' : '#fff3e0',
                              color: q.isResolved ? '#2e7d32' : '#ef6c00',
                              fontWeight: '600'
                            }}>
                              {q.isResolved ? 'Resolved' : 'Pending'}
                            </span>
                            <button 
                              className="btn-secondary" 
                              style={{ padding: '4px 10px', fontSize: '0.8rem' }} 
                              onClick={() => {
                                setSelectedQuestion(q);
                                setForumSubView('answers');
                              }}
                            >
                              View Answers
                            </button>
                          </div>
                        </div>
                      )) : <p className="subtext">No questions yet. Ask our agronomists for expert advice!</p>}
                    </div>
                  </>
                ) : forumSubView === 'ask' ? (
                  <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
                    <div style={{ marginBottom: '20px' }}>
                      <button className="btn-secondary" onClick={() => setForumSubView('list')}>← Back to Questions</button>
                    </div>
                    <h3>Ask a New Question</h3>
                    <form onSubmit={submitQuestion} style={{ marginTop: '20px' }}>
                      <div className="form-group" style={{ marginBottom: '15px' }}>
                        <label>Question Title</label>
                        <input 
                          type="text" 
                          className="input-field" 
                          placeholder="e.g. How to manage maize rust?" 
                          value={newQuestion.title}
                          onChange={(e) => setNewQuestion({...newQuestion, title: e.target.value})}
                          required
                        />
                      </div>
                      <div className="form-group" style={{ marginBottom: '15px' }}>
                        <label>Full Details</label>
                        <textarea 
                          className="input-field" 
                          style={{ minHeight: '120px', resize: 'vertical' }}
                          placeholder="Describe your farming issue in detail..." 
                          value={newQuestion.content}
                          onChange={(e) => setNewQuestion({...newQuestion, content: e.target.value})}
                          required
                        />
                      </div>
                      <button type="submit" className="btn-primary" style={{ width: 'auto' }}>Post Question</button>
                    </form>
                  </motion.div>
                ) : (
                  <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
                    <div style={{ marginBottom: '20px' }}>
                      <button className="btn-secondary" onClick={() => setForumSubView('list')}>← Back to Forum</button>
                    </div>
                    <h3>Answers for: {selectedQuestion?.title}</h3>
                    <p style={{ 
                      marginTop: '10px', padding: '15px', background: 'rgba(0,0,0,0.02)', 
                      borderRadius: '8px', fontStyle: 'italic', color: '#555' 
                    }}>
                      "{selectedQuestion?.content}"
                    </p>
                    
                    <div style={{ marginTop: '30px' }}>
                      <h4>Expert Responses</h4>
                      <div style={{ marginTop: '15px' }}>
                        {selectedQuestion?.answers && selectedQuestion.answers.length > 0 ? (
                          selectedQuestion.answers.map(ans => (
                            <div key={ans.id} style={{ 
                              padding: '20px', background: '#fff', border: '1px solid #eee', 
                              borderRadius: '12px', marginBottom: '15px', boxShadow: '0 2px 4px rgba(0,0,0,0.02)'
                            }}>
                              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
                                <strong style={{ color: '#2e7d32' }}>Dr. {ans.agronomist?.fullName || 'Expert Agronomist'}</strong>
                                <span style={{ fontSize: '0.8rem', color: '#888' }}>{new Date(ans.createdAt).toLocaleDateString()}</span>
                              </div>
                              <p style={{ lineHeight: '1.6' }}>{ans.content}</p>
                            </div>
                          ))
                        ) : (
                          <div style={{ padding: '20px', background: '#fff3e0', color: '#ef6c00', borderRadius: '8px' }}>
                            No experts have responded to this question yet. Please check back later.
                          </div>
                        )}
                      </div>
                    </div>
                  </motion.div>
                )}
              </motion.div>
            )}
          </section>
        </main>
      </div>
      <Footer />
    </>
  );
};

export default FarmerDashboard;
