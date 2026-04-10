import React, { useState } from 'react';
import Login from './components/Login';
import Signup from './components/Signup';

const App = () => {
  const [user, setUser] = useState(null);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
  };

  return (
    <div>
      <h1>Smart Farming Advisory System</h1>
      {user ? (
        <div>
          <p>Welcome, {user.username}!</p>
          <button onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <>
          <Login onLoginSuccess={handleLoginSuccess} />
          <hr />
          <Signup />
        </>
      )}
    </div>
  );
};

export default App;
