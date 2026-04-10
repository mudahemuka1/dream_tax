const API_BASE = '/api';

// --- Authentication & Initialization ---
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        try {
            const res = await fetch(`${API_BASE}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });
            const data = await res.json();
            if (res.ok) {
                localStorage.setItem('user', JSON.stringify(data));
                window.location.href = 'dashboard.html';
            } else { alert(data.message || 'Login Failed'); }
        } catch (err) { alert('Connection Error!'); }
    });
}

// Handle Signup
const signupForm = document.getElementById('signupForm');
if (signupForm) {
    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('sEmail').value;
        const password = document.getElementById('sPassword').value;
        const role = document.getElementById('sRole').value;
        const fullName = document.getElementById('sFullName').value;
        const location = document.getElementById('sLocation').value;
        const contactNumber = document.getElementById('sContact').value;
        const specialization = document.getElementById('sSpecial').value;
        const companyName = document.getElementById('sCompany').value;
        
        const signupBtn = document.getElementById('signupBtn');
        const loader = document.getElementById('loader');
        const errorBox = document.getElementById('errorBox');
        
        // Advanced Validation (Phase 10)
        const validationError = validateSignup(email, password);
        if (validationError) {
            errorBox.textContent = validationError;
            errorBox.style.display = 'block';
            return;
        }

        signupBtn.style.display = 'none';
        loader.style.display = 'block';
        errorBox.style.display = 'none';

        try {
            const res = await fetch(`${API_BASE}/auth/signup`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password, role, fullName, location, contactNumber, specialization, companyName })
            });
            const data = await res.json();
            if (res.ok) {
                alert('Account created! A verification code has been sent to your email.');
                window.location.href = `verify.html?email=${encodeURIComponent(email)}`;
            } else { throw new Error(data.message); }
        } catch (err) {
            errorBox.textContent = err.message;
            errorBox.style.display = 'block';
            signupBtn.style.display = 'block';
            loader.style.display = 'none';
        }
    });
}

// Handle Verification
const verifyForm = document.getElementById('verifyForm');
if (verifyForm) {
    const params = new URLSearchParams(window.location.search);
    const email = params.get('email');
    if (email) document.getElementById('displayEmail').textContent = email;

    verifyForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const code = document.getElementById('vCode').value;
        const errorBox = document.getElementById('errorBox');
        try {
            const res = await fetch(`${API_BASE}/auth/verify?email=${encodeURIComponent(email)}&code=${code}`, { method: 'POST' });
            const data = await res.json();
            if (res.ok) {
                alert('Success! Your account is activated.');
                window.location.href = 'index.html';
            } else { throw new Error(data.message); }
        } catch (err) {
            errorBox.textContent = err.message;
            errorBox.style.display = 'block';
        }
    });
}

async function resendCode() {
    const params = new URLSearchParams(window.location.search);
    const email = params.get('email');
    try {
        const res = await fetch(`${API_BASE}/auth/resend-code?email=${encodeURIComponent(email)}`, { method: 'POST' });
        if (res.ok) alert('New code sent!'); else alert('Failed to resend');
    } catch (err) { alert('Error!'); }
}

document.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname.endsWith('dashboard.html')) {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) { window.location.href = 'index.html'; return; }

        document.getElementById('welcomeTitle').textContent = `Welcome, ${user.name || user.email.split('@')[0]}!`;
        document.getElementById('userSubtext').textContent = user.role;

        if (user.role === 'ADMIN') {
            document.getElementById('navAdmin').style.display = 'block';
            fetchUsers(); fetchHistory();
        } else if (user.role === 'AGRONOMIST') {
            document.getElementById('navAgronomist').style.display = 'block';
            fetchPendingQuestions();
        } else if (user.role === 'FARMER') {
            document.getElementById('navFarmer').style.display = 'block';
            fetchMyQuestions();
        }
    }
});

function showSection(id) {
    document.querySelectorAll('.section').forEach(s => s.style.display = 'none');
    document.getElementById(id).style.display = 'block';
    
    document.querySelectorAll('.nav-link').forEach(l => {
        l.classList.remove('active');
        if (l.innerText.toLowerCase().includes(id.split('-')[0]) || (id === 'overview' && l.innerText.toLowerCase() === 'dashboard')) {
            l.classList.add('active');
        }
    });

    if (id === 'manage-data') fetchHistory();
    if (id === 'expert-answering') fetchPendingQuestions();
}

// --- Farmer Actions ---
async function askQuestion() {
    const user = JSON.parse(localStorage.getItem('user'));
    const payload = {
        farmerId: user.id,
        title: document.getElementById('qTitle').value,
        content: document.getElementById('qContent').value
    };
    const res = await fetch(`${API_BASE}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${user.token}` },
        body: JSON.stringify(payload)
    });
    if (res.ok) { alert('Consultation Shared!'); document.getElementById('qTitle').value=''; document.getElementById('qContent').value=''; fetchMyQuestions(); }
}

async function fetchMyQuestions() {
    const user = JSON.parse(localStorage.getItem('user'));
    const res = await fetch(`${API_BASE}/questions/farmer/${user.id}`, { headers: { 'Authorization': `Bearer ${user.token}` } });
    const data = await res.json();
    document.getElementById('myQuestions').innerHTML = data.map(q => `
        <div class="forum-card">
            <span class="badge ${q.isResolved ? 'badge-resolved' : 'badge-pending'}">${q.isResolved ? 'RESOLVED' : 'PENDING'}</span>
            <h4 style="margin: 10px 0">${q.title}</h4>
            <p>${q.content}</p>
            ${q.answers ? q.answers.map(a => `<div style="margin-top:10px; padding:10px; background:#fff; border-radius:8px"><strong>Expert Advice:</strong> ${a.content}</div>`).join('') : ''}
        </div>
    `).reverse().join('');
}

async function getRecommendations() {
    const soil = document.getElementById('soilType').value;
    const season = document.getElementById('season').value;
    const res = await fetch(`${API_BASE}/crops/recommend?soil=${soil}&season=${season}`);
    const data = await res.json();
    const container = document.getElementById('recResults');
    container.innerHTML = data.length === 0 ? '<p style="color:red; margin-top:20px">No matching crops.</p>' : data.map(c => `
        <div class="result-item"><h4>${c.name}</h4><p>${c.description}</p></div>
    `).join('');
}

// --- Agronomist Actions ---
async function fetchPendingQuestions() {
    const user = JSON.parse(localStorage.getItem('user'));
    const res = await fetch(`${API_BASE}/questions`, { headers: { 'Authorization': `Bearer ${user.token}` } });
    const data = await res.json();
    const pending = data.filter(q => !q.isResolved);
    document.getElementById('pendingQuestions').innerHTML = pending.length === 0 ? '<p>No pending consultations.</p>' : pending.map(q => `
        <div class="glass-card">
            <h4>${q.title}</h4><p>${q.content}</p>
            <textarea id="ans-${q.id}" placeholder="Provide expert response..." style="margin:10px 0"></textarea>
            <button class="btn-action" onclick="submitAnswer(${q.id})">SUBMIT ADVICE</button>
        </div>
    `).join('');
}

async function submitAnswer(qId) {
    const user = JSON.parse(localStorage.getItem('user'));
    const content = document.getElementById(`ans-${qId}`).value;
    await fetch(`${API_BASE}/questions/${qId}/answers`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${user.token}` },
        body: JSON.stringify({ agronomistId: user.id, content })
    });
    alert('Advice Sent!'); fetchPendingQuestions();
}

// --- Admin Actions ---
async function fetchUsers() {
    const user = JSON.parse(localStorage.getItem('user'));
    const res = await fetch(`${API_BASE}/auth/users`, { headers: { 'Authorization': `Bearer ${user.token}` } });
    const data = await res.json();
    document.getElementById('userTbody').innerHTML = data.map(u => `<tr><td>${u.email}</td><td>${u.role}</td><td>${new Date(u.createdAt).toLocaleDateString()}</td></tr>`).join('');
}

async function fetchHistory() {
    const res = await fetch(`${API_BASE}/crops`);
    const data = await res.json();
    document.getElementById('cropTbody').innerHTML = data.map(c => `<tr><td>${c.name}</td><td>${c.suitableSoilType}</td><td>${c.suitableSeason}</td></tr>`).join('');
}

// --- Validation Utilities ---
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validateSignup(email, password) {
    if (!isValidEmail(email)) return "Please enter a valid institutional email.";
    if (password.length < 6) return "Password must be at least 6 characters for security.";
    return null;
}

// --- Live Filtering ---
function filterTable(inputId, tableBodyId) {
    const input = document.getElementById(inputId);
    const filter = input.value.toUpperCase();
    const tbody = document.getElementById(tableBodyId);
    const tr = tbody.getElementsByTagName("tr");

    for (let i = 0; i < tr.length; i++) {
        const td = tr[i].getElementsByTagName("td");
        let found = false;
        for (let j = 0; j < td.length; j++) {
            if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                found = true; break;
            }
        }
        tr[i].style.display = found ? "" : "none";
    }
}

function logout() { localStorage.removeItem('user'); window.location.href = 'index.html'; }
