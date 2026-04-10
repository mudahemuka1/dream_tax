import React from 'react';
import Navbar from './Navbar';
import Footer from './Footer';

const Layout = ({ children, showFooter = true }) => {
  return (
    <>
      <Navbar />
      <div style={{ marginTop: '80px' }}>
        {children}
      </div>
      {showFooter && <Footer />}
    </>
  );
};

export default Layout;
