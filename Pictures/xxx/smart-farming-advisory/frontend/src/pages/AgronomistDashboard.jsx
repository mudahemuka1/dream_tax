import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import authService from '../api/authService';
import api from '../api/api';
import { motion, AnimatePresence } from 'framer-motion';
import { HelpCircle, MessageSquare } from 'lucide-react';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

const AgronomistDashboard = () => {
  const user = authService.getCurrentUser();
  const [questions, setQuestions] = useState([]);
  const [reply, setReply] = useState({ qId: null, content: '' });

  useEffect(() => {
    fetchQuestions();
  }, []);

  const fetchQuestions = async () => {
    try {
      const res = await api.get('/questions');
      setQuestions(res.data.filter(q => !q.isResolved));
    } catch (err) {
      console.error(err);
    }
  };

  const handleReply = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        content: reply.content,
        agronomistId: user.id
      };
      await api.post(`/questions/${reply.qId}/answers`, payload);
      alert('Response sent!');
      setReply({ qId: null, content: '' });
      fetchQuestions();
    } catch (err) {
      alert('Failed to send response');
    }
  };

  return (
    <>
      <Navbar />
      <div className="dashboard-container" style={{ marginTop: '70px' }}>
        <Sidebar user={user} />
        <div className="main-content">
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
            <header style={{ marginBottom: '40px' }}>
              <h1 className="heading" style={{ textAlign: 'left', marginBottom: '10px' }}>Agronomist Portal</h1>
              <p className="subtext" style={{ textAlign: 'left', marginTop: '0' }}>Helping farmers make better decisions</p>
              <p style={{ fontWeight: 600 }}>Welcome, {user?.name || user?.email}</p>
            </header>

            <section>
              <h2 style={{ marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '10px' }}>
                 <MessageSquare color="#2e7d32" /> Pending Farmer Inquiries 
              </h2>
              <div className="card-grid">
                <AnimatePresence>
                  {questions.length > 0 ? questions.map(q => (
                    <motion.div 
                      layout
                      key={q.id} 
                      className="data-card"
                      exit={{ opacity: 0, scale: 0.9 }}
                    >
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <h4>{q.title}</h4>
                        <span style={{ fontSize: '0.75rem', padding: '2px 8px', background: '#e8f5e9', color: '#2e7d32', borderRadius: '10px' }}>
                          From: {q.farmer?.fullName || 'Farmer'}
                        </span>
                      </div>
                      <p style={{ margin: '10px 0', fontSize: '0.9rem' }}>{q.content}</p>
                      <button 
                        className="btn-primary" 
                        onClick={() => setReply({ ...reply, qId: q.id })}
                        style={{ padding: '8px 16px', fontSize: '0.9rem', width: 'auto' }}
                      >
                        Respond
                      </button>
                      {reply.qId === q.id && (
                        <div style={{ marginTop: '20px' }}>
                          <textarea 
                            className="input-field" 
                            placeholder="Type your expert advice here..."
                            rows="4"
                            onChange={e => setReply({ ...reply, content: e.target.value })}
                          />
                          <div style={{ display: 'flex', gap: '10px' }}>
                            <button className="btn-primary" onClick={handleReply} style={{ width: 'auto' }}>Submit</button>
                            <button className="btn-primary" style={{ width: 'auto', background: '#ccc' }} onClick={() => setReply({ qId: null, content: '' })}>Cancel</button>
                          </div>
                        </div>
                      )}
                    </motion.div>
                  )) : <p>No pending questions at the moment. Good job!</p>}
                </AnimatePresence>
              </div>
            </section>
          </motion.div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default AgronomistDashboard;
