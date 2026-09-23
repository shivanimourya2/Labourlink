/* ============================================
   LabourLink — Core JavaScript v2
   i18n, Voice Nav, Notifications, Photo Upload
============================================ */

const API_BASE = '/api';

// ===== API Helper =====
async function api(endpoint, options = {}) {
    try {
        const config = {
            headers: { 'Content-Type': 'application/json' },
            ...options,
        };
        if (config.body && typeof config.body === 'object' && !(config.body instanceof FormData)) {
            config.body = JSON.stringify(config.body);
        }
        if (config.body instanceof FormData) {
            delete config.headers['Content-Type'];
        }
        const response = await fetch(`${API_BASE}${endpoint}`, config);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('API Error:', error);
        return { success: false, message: 'Network error' };
    }
}

// ===== Auth =====
function requireAuth() {
    const user = JSON.parse(localStorage.getItem('user') || 'null');
    if (!user) {
        window.location.href = '/login.html';
        return null;
    }
    return user;
}

function getUser() {
    return JSON.parse(localStorage.getItem('user') || 'null');
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = '/';
}

// ===== UI Helpers =====
function getInitials(name) {
    if (!name) return '?';
    return name.split(' ').map(w => w[0]).join('').toUpperCase().substring(0, 2);
}

function formatCurrency(amount) {
    return '₹' + (amount || 0).toLocaleString('en-IN');
}

function formatDate(d) {
    if (!d) return '—';
    return new Date(d).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}

function formatTimeAgo(dateStr) {
    if (!dateStr) return '';
    const diff = Date.now() - new Date(dateStr).getTime();
    const mins = Math.floor(diff / 60000);
    if (mins < 1) return 'Just now';
    if (mins < 60) return `${mins}m ago`;
    const hrs = Math.floor(mins / 60);
    if (hrs < 24) return `${hrs}h ago`;
    return `${Math.floor(hrs / 24)}d ago`;
}

const SKILL_MAP = {
    MASON: { icon: '🧱', name: 'Mason' },
    ELECTRICIAN: { icon: '⚡', name: 'Electrician' },
    PAINTER: { icon: '🎨', name: 'Painter' },
    LABOURER: { icon: '💪', name: 'Labourer' },
    PLUMBER: { icon: '🔧', name: 'Plumber' },
    CARPENTER: { icon: '🪚', name: 'Carpenter' },
    WELDER: { icon: '🔥', name: 'Welder' },
    TILE_FITTER: { icon: '🔲', name: 'Tile Fitter' },
    ROD_BINDER: { icon: '🏗️', name: 'Rod Binder' },
    HELPER: { icon: '🤝', name: 'Helper' },
};

function skillDisplay(skill) {
    const s = SKILL_MAP[skill];
    return s ? `<span class="job-meta-item">${s.icon} ${s.name}</span>` : (skill || '');
}

function statusBadge(status) {
    const sm = { OPEN: '🟢', ONGOING: '🔨', COMPLETED: '✅', APPLIED: '📝', SELECTED: '✅', REJECTED: '❌' };
    return `<span class="status-badge status-${status}">${sm[status] || ''} ${status}</span>`;
}

function renderStars(rating) {
    const r = Math.round(rating || 0);
    return '★'.repeat(r) + '☆'.repeat(5 - r);
}

// ===== Toast =====
function showToast(message, type = 'info') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }
    const icons = { success: '✅', error: '❌', warning: '⚠️', info: 'ℹ️' };
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `<span>${icons[type] || ''}</span><span>${message}</span>`;
    container.prepend(toast);
    setTimeout(() => { toast.style.opacity = '0'; setTimeout(() => toast.remove(), 300); }, 4000);
}

// ===== Modal =====
function openModal(id) { document.getElementById(id).classList.add('open'); }
function closeModal(id) { document.getElementById(id).classList.remove('open'); }

// ===================================================================
// ===== i18n — Multi-language Support =====
// ===================================================================
let currentLocale = {};
let currentLang = localStorage.getItem('lang') || 'en';

async function loadLanguage(lang) {
    try {
        const res = await fetch(`/locales/${lang}.json`);
        currentLocale = await res.json();
        currentLang = lang;
        localStorage.setItem('lang', lang);
        applyTranslations();
        updateLangButtons();
    } catch (e) {
        console.error('Failed to load locale:', lang, e);
    }
}

function t(key) {
    return currentLocale[key] || key;
}

function applyTranslations() {
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (currentLocale[key]) {
            if (el.tagName === 'INPUT' && el.type !== 'submit') {
                el.placeholder = currentLocale[key];
            } else if (el.tagName === 'OPTION') {
                el.textContent = currentLocale[key];
            } else {
                el.textContent = currentLocale[key];
            }
        }
    });
}

function updateLangButtons() {
    document.querySelectorAll('.lang-btn').forEach(b => {
        b.classList.toggle('active', b.dataset.lang === currentLang);
    });
}

// ===================================================================
// ===== Navbar Renderer =====
// ===================================================================
function renderNavbar(activePage) {
    const user = getUser();
    const navbar = document.getElementById('navbar');
    if (!navbar) return;

    let links = '';

    const langSwitcher = `
        <div class="lang-switcher">
            <button class="lang-btn ${currentLang === 'en' ? 'active' : ''}" data-lang="en" onclick="loadLanguage('en')">EN</button>
            <button class="lang-btn ${currentLang === 'hi' ? 'active' : ''}" data-lang="hi" onclick="loadLanguage('hi')">हिं</button>
            <button class="lang-btn ${currentLang === 'mr' ? 'active' : ''}" data-lang="mr" onclick="loadLanguage('mr')">मरा</button>
        </div>
    `;

    if (user) {
        const dashUrl = user.role === 'WORKER' ? '/worker-dashboard.html' : '/contractor-dashboard.html';
        const profileBit = user.role === 'WORKER'
            ? `<a href="/worker-profile.html?id=${user.id}" class="btn btn-sm btn-secondary" data-i18n="profile">🧑 Profile</a>`
            : '';

        links = `
            ${langSwitcher}
            <button class="notif-bell" onclick="toggleNotifPanel()" id="notifBellBtn">
                🔔
                <span class="notif-badge" id="notifCount" style="display: none;">0</span>
            </button>
            <a href="${dashUrl}" class="btn btn-sm ${activePage === 'dashboard' ? 'btn-primary' : 'btn-secondary'}" data-i18n="dashboard">🏠 Dashboard</a>
            ${profileBit}
            <span style="color: var(--text-muted); font-size: 0.9rem;">Hi, ${user.name.split(' ')[0]}</span>
            <button class="btn btn-sm btn-danger" onclick="logout()" data-i18n="logout">Logout</button>
        `;
    } else {
        links = `
            ${langSwitcher}
            <a href="/login.html" class="btn btn-sm btn-secondary" data-i18n="login">Login</a>
            <a href="/signup.html" class="btn btn-sm btn-primary" data-i18n="signup">Sign Up</a>
        `;
    }

    navbar.innerHTML = `
        <a href="/" class="nav-brand">
            <div class="nav-brand-icon">🏗️</div>
            <span data-i18n="app_name">LabourLink</span>
        </a>
        <div class="nav-links">${links}</div>
    `;

    // Notification panel
    if (user && !document.getElementById('notifPanel')) {
        const panel = document.createElement('div');
        panel.className = 'notif-panel';
        panel.id = 'notifPanel';
        panel.innerHTML = `
            <div class="notif-panel-header">
                <span data-i18n="notifications">🔔 Notifications</span>
                <button class="btn btn-sm btn-secondary" onclick="markAllNotifRead()" data-i18n="mark_all_read">Mark all read</button>
            </div>
            <div class="notif-panel-body" id="notifPanelBody">
                <div class="spinner"></div>
            </div>
        `;
        document.body.appendChild(panel);
        loadNotifications();
    }

    // Re-apply translations after render
    applyTranslations();
}

// ===================================================================
// ===== Notifications =====
// ===================================================================
let notifPollInterval = null;

async function loadNotifications() {
    const user = getUser();
    if (!user) return;

    const result = await api(`/notifications/${user.id}`);
    if (!result.success) return;

    const { notifications, unreadCount } = result.data;
    const badge = document.getElementById('notifCount');
    if (badge) {
        if (unreadCount > 0) {
            badge.style.display = 'flex';
            badge.textContent = unreadCount > 9 ? '9+' : unreadCount;
        } else {
            badge.style.display = 'none';
        }
    }

    const body = document.getElementById('notifPanelBody');
    if (body) {
        if (!notifications || notifications.length === 0) {
            body.innerHTML = `<div style="text-align: center; padding: 30px; color: var(--text-muted);" data-i18n="no_notifications">No notifications</div>`;
        } else {
            body.innerHTML = notifications.slice(0, 20).map(n => `
                <div class="notif-item ${n.readStatus ? '' : 'unread'}" onclick="markNotifRead(${n.id})">
                    <div>${n.message}</div>
                    <div class="notif-time">${formatTimeAgo(n.createdAt)}</div>
                </div>
            `).join('');
        }
    }

    // Show browser notification for new urgent ones
    if (unreadCount > 0 && Notification.permission === 'granted') {
        const urgent = notifications.find(n => !n.readStatus && n.type === 'URGENT_JOB');
        if (urgent) {
            new Notification('🔥 LabourLink — Urgent Job!', {
                body: urgent.message,
                icon: '/favicon.ico'
            });
        }
    }
}

function toggleNotifPanel() {
    const panel = document.getElementById('notifPanel');
    if (panel) {
        panel.classList.toggle('open');
        if (panel.classList.contains('open')) loadNotifications();
    }
}

async function markNotifRead(id) {
    await api(`/notifications/${id}/read`, { method: 'POST' });
    loadNotifications();
}

async function markAllNotifRead() {
    const user = getUser();
    if (!user) return;
    await api(`/notifications/read-all/${user.id}`, { method: 'POST' });
    loadNotifications();
    showToast('All notifications marked as read', 'success');
}

// Poll for notifications every 30s
function startNotifPolling() {
    if (notifPollInterval) clearInterval(notifPollInterval);
    notifPollInterval = setInterval(loadNotifications, 30000);
}

// Close notif panel when clicking outside
document.addEventListener('click', (e) => {
    const panel = document.getElementById('notifPanel');
    const bell = document.getElementById('notifBellBtn');
    if (panel && panel.classList.contains('open') &&
        !panel.contains(e.target) && !bell.contains(e.target)) {
        panel.classList.remove('open');
    }
});

// ===================================================================
// ===== Request Browser Notification Permission =====
// ===================================================================
function requestNotifPermission() {
    if ('Notification' in window && Notification.permission === 'default') {
        Notification.requestPermission().then(perm => {
            if (perm === 'granted') {
                showToast('📲 Notifications enabled!', 'success');
            }
        });
    }
}

// ===================================================================
// ===== Voice Navigation (Web Speech API) =====
// ===================================================================
let voiceRecognition = null;
let isListening = false;

function initVoiceNav() {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRecognition) {
        console.log('Speech recognition not supported');
        return;
    }

    voiceRecognition = new SpeechRecognition();
    voiceRecognition.continuous = false;
    voiceRecognition.interimResults = false;
    voiceRecognition.lang = currentLang === 'hi' ? 'hi-IN' : currentLang === 'mr' ? 'mr-IN' : 'en-IN';

    voiceRecognition.onresult = (event) => {
        const transcript = event.results[0][0].transcript.toLowerCase().trim();
        console.log('Voice:', transcript);
        showVoiceFeedback(`🎤 "${transcript}"`);
        handleVoiceCommand(transcript);
    };

    voiceRecognition.onend = () => {
        isListening = false;
        const fab = document.getElementById('voiceFab');
        if (fab) fab.classList.remove('listening');
    };

    voiceRecognition.onerror = (err) => {
        isListening = false;
        const fab = document.getElementById('voiceFab');
        if (fab) fab.classList.remove('listening');
        if (err.error !== 'no-speech') {
            showVoiceFeedback('❌ Could not understand. Try again.');
        }
    };
}

function toggleVoice() {
    if (!voiceRecognition) {
        initVoiceNav();
        if (!voiceRecognition) {
            showToast('Voice not supported in this browser', 'error');
            return;
        }
    }

    // Update language
    voiceRecognition.lang = currentLang === 'hi' ? 'hi-IN' : currentLang === 'mr' ? 'mr-IN' : 'en-IN';

    const fab = document.getElementById('voiceFab');
    if (isListening) {
        voiceRecognition.stop();
        isListening = false;
        if (fab) fab.classList.remove('listening');
    } else {
        voiceRecognition.start();
        isListening = true;
        if (fab) fab.classList.add('listening');
        showVoiceFeedback('🎤 Listening... Say a command');
    }
}

function handleVoiceCommand(cmd) {
    // English commands
    if (cmd.includes('show job') || cmd.includes('browse job') || cmd.includes('find job') || cmd.includes('काम दिखाओ') || cmd.includes('काम शोधा')) {
        document.querySelector('[onclick*="switchTab(\'browse\')"]')?.click();
        speakBack('Showing available jobs');
        if (typeof loadJobs === 'function') loadJobs();
    }
    else if (cmd.includes('my application') || cmd.includes('मेरे आवेदन') || cmd.includes('माझे अर्ज')) {
        document.querySelector('[onclick*="switchTab(\'applications\')"]')?.click();
        speakBack('Here are your applications');
    }
    else if (cmd.includes('my profile') || cmd.includes('profile') || cmd.includes('प्रोफ़ाइल') || cmd.includes('प्रोफाइल')) {
        const user = getUser();
        if (user) window.location.href = `/worker-profile.html?id=${user.id}`;
        speakBack('Opening your profile');
    }
    else if (cmd.includes('history') || cmd.includes('work history') || cmd.includes('इतिहास')) {
        document.querySelector('[onclick*="switchTab(\'history\')"]')?.click();
        speakBack('Showing your work history');
    }
    else if (cmd.startsWith('apply') || cmd.includes('apply to job') || cmd.includes('आवेदन') || cmd.includes('अर्ज करा')) {
        const match = cmd.match(/(\d+)/);
        if (match && typeof applyToJobByIndex === 'function') {
            applyToJobByIndex(parseInt(match[1]) - 1);
            speakBack(`Applying to job number ${match[1]}`);
        } else {
            speakBack('Please say the job number. For example, apply to job number 1.');
        }
    }
    else if (cmd.includes('refresh') || cmd.includes('रिफ्रेश')) {
        location.reload();
    }
    else if (cmd.includes('notification') || cmd.includes('सूचना')) {
        toggleNotifPanel();
        speakBack('Showing your notifications');
    }
    else {
        speakBack('Command not recognized. Try: show jobs, my profile, apply to job 1, or refresh.');
    }
}

function speakBack(text) {
    if ('speechSynthesis' in window) {
        const utter = new SpeechSynthesisUtterance(text);
        utter.lang = currentLang === 'hi' ? 'hi-IN' : currentLang === 'mr' ? 'mr-IN' : 'en-IN';
        utter.rate = 0.9;
        window.speechSynthesis.speak(utter);
    }
    showVoiceFeedback(`🔊 ${text}`);
}

function showVoiceFeedback(text) {
    let fb = document.getElementById('voiceFeedback');
    if (!fb) {
        fb = document.createElement('div');
        fb.className = 'voice-feedback';
        fb.id = 'voiceFeedback';
        document.body.appendChild(fb);
    }
    fb.textContent = text;
    fb.classList.add('visible');
    setTimeout(() => fb.classList.remove('visible'), 4000);
}

// ===================================================================
// ===== Photo Upload Helper =====
// ===================================================================
async function uploadPhotos(fileInput) {
    const files = fileInput.files;
    if (!files || files.length === 0) return [];

    const formData = new FormData();
    for (let i = 0; i < Math.min(files.length, 2); i++) {
        formData.append('files', files[i]);
    }

    try {
        const response = await fetch(`${API_BASE}/upload`, {
            method: 'POST',
            body: formData
        });
        const result = await response.json();
        return result.success ? result.data : [];
    } catch (e) {
        console.error('Upload error:', e);
        return [];
    }
}

// ===================================================================
// ===== 3D Animated Background =====
// ===================================================================
function render3DBackground() {
    // Check if already rendered or if bg-3d container exists
    if (document.querySelector('.bg-3d')) return;

    const bg = document.createElement('div');
    bg.className = 'bg-3d';
    bg.innerHTML = `
        <div class="bg-orb bg-orb-1"></div>
        <div class="bg-orb bg-orb-2"></div>
        <div class="bg-orb bg-orb-3"></div>
    `;
    document.body.prepend(bg);

    // Grid overlay
    if (!document.querySelector('.bg-grid')) {
        const grid = document.createElement('div');
        grid.className = 'bg-grid';
        document.body.insertBefore(grid, bg.nextSibling);
    }
}

// ===================================================================
// ===== Geolocation — Fill Current Location =====
// ===================================================================
async function fillCurrentLocation(inputId) {
    const input = document.getElementById(inputId);
    const btn = event?.target?.closest('.location-btn');

    if (!navigator.geolocation) {
        showToast('Geolocation is not supported by your browser', 'error');
        return;
    }

    // Show loading state
    if (btn) {
        btn.classList.add('loading');
        btn.innerHTML = '<div class="spinner-sm"></div> Detecting...';
    }

    navigator.geolocation.getCurrentPosition(
        async (position) => {
            const { latitude, longitude } = position.coords;
            try {
                // Reverse geocode using OpenStreetMap Nominatim — zoom=18 for max specificity
                const response = await fetch(
                    `https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}&zoom=18&addressdetails=1`,
                    { headers: { 'Accept-Language': 'en' } }
                );
                const data = await response.json();

                // Build a specific address string
                const addr = data.address || {};
                const parts = [];

                // Most specific first
                if (addr.neighbourhood || addr.suburb || addr.hamlet) {
                    parts.push(addr.neighbourhood || addr.suburb || addr.hamlet);
                }
                if (addr.road) parts.push(addr.road);
                if (addr.city || addr.town || addr.village) {
                    parts.push(addr.city || addr.town || addr.village);
                }
                if (addr.state_district && addr.state_district !== (addr.city || addr.town)) {
                    parts.push(addr.state_district);
                }
                if (addr.state) parts.push(addr.state);

                // Fallback to display_name if parts are empty
                const fullLocation = parts.length > 0 ? parts.join(', ') : (data.display_name || 'Unknown');

                input.value = fullLocation;
                showToast(`📍 ${fullLocation}`, 'success');

                // Store coordinates in hidden fields if they exist
                const latField = document.getElementById('latitude');
                const lonField = document.getElementById('longitude');
                if (latField) latField.value = latitude;
                if (lonField) lonField.value = longitude;

            } catch (error) {
                console.error('Reverse geocoding error:', error);
                showToast('Could not determine location', 'error');
            }

            // Reset button
            if (btn) {
                btn.classList.remove('loading');
                btn.innerHTML = '📍 Use My Location';
            }
        },
        (error) => {
            // Reset button
            if (btn) {
                btn.classList.remove('loading');
                btn.innerHTML = '📍 Use My Location';
            }

            switch (error.code) {
                case error.PERMISSION_DENIED:
                    showToast('Location permission denied. Please allow location access.', 'error');
                    break;
                case error.POSITION_UNAVAILABLE:
                    showToast('Location unavailable', 'error');
                    break;
                case error.TIMEOUT:
                    showToast('Location request timed out', 'error');

                    break;
                default:
                    showToast('Could not get location', 'error');
            }
        },
        {
            enableHighAccuracy: false,
            timeout: 10000,
            maximumAge: 300000 // Cache for 5 minutes
        }
    );
}

// ===================================================================
// ===== Init — Run on every page =====
// ===================================================================
document.addEventListener('DOMContentLoaded', async () => {
    // Render 3D animated background on all pages
    render3DBackground();

    await loadLanguage(currentLang);
    const user = getUser();
    if (user) {
        startNotifPolling();
        requestNotifPermission();
    }
});
