import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import VerifyAccount from './pages/VerifyAccount';
import FarmerDashboard from './pages/FarmerDashboard';
import AgronomistDashboard from './pages/AgronomistDashboard';
import AdminDashboard from './pages/AdminDashboard';
import AgroDealerDashboard from './pages/AgroDealerDashboard';
import authService from './api/authService';

const ProtectedRoute = ({ children, allowedRoles }) => {
  const user = authService.getCurrentUser();
  if (!user) return <Navigate to="/login" />;
  if (allowedRoles && !allowedRoles.includes(user.role)) return <Navigate to="/" />;
  return children;
};

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/verify" element={<VerifyAccount />} />
        
        <Route 
          path="/dash/farmer" 
          element={<ProtectedRoute allowedRoles={['FARMER']}><FarmerDashboard /></ProtectedRoute>} 
        />
        
        <Route 
          path="/dash/agronomist" 
          element={<ProtectedRoute allowedRoles={['AGRONOMIST']}><AgronomistDashboard /></ProtectedRoute>} 
        />

        <Route 
          path="/dash/admin" 
          element={<ProtectedRoute allowedRoles={['ADMIN']}><AdminDashboard /></ProtectedRoute>} 
        />

        <Route 
          path="/dash/dealer" 
          element={<ProtectedRoute allowedRoles={['AGRO_DEALER']}><AgroDealerDashboard /></ProtectedRoute>} 
        />
      </Routes>
    </Router>
  );
}

export default App;
