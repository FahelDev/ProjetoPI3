/* ── CONFIGURAÇÕES E ESTADO ── */
const API_URL = "http://localhost:8080/api/auth";

/* ── INICIALIZAÇÃO ── */
document.addEventListener('DOMContentLoaded', () => {
    // 1. Configura as Abas
    setupTabs();
    // 2. Configura os Olhinhos (Mostrar Senha)
    setupPasswordToggle();
    // 3. Configura a Força da Senha
    setupPasswordStrength();
    // 4. Configura os Envia de Formulários
    setupFormSubmissions();
});

/* ── LOGICA DE ABAS (VISUAL) ── */
function setupTabs() {
    const tabLogin = document.getElementById('tab-login');
    const tabRegister = document.getElementById('tab-register');
    const linkGoReg = document.getElementById('link-go-register');

    const switchTab = (tab) => {
        const panelLogin = document.getElementById('panel-login');
        const panelRegister = document.getElementById('panel-register');
        const footerText = document.getElementById('footer-text');

        if (tab === 'login') {
            tabLogin.classList.add('active'); tabRegister.classList.remove('active');
            panelLogin.classList.add('active'); panelRegister.classList.remove('active');
            footerText.innerHTML = 'Não tem conta? <a href="#" id="link-go-register">Cadastre-se</a>';
            // Re-adiciona o evento ao novo link gerado no innerHTML
            document.getElementById('link-go-register').onclick = (e) => { e.preventDefault(); switchTab('register'); };
        } else {
            tabRegister.classList.add('active'); tabLogin.classList.remove('active');
            panelRegister.classList.add('active'); panelLogin.classList.remove('active');
            footerText.innerHTML = 'Já tem uma conta? <a href="#" id="link-go-login">Entrar agora</a>';
            document.getElementById('link-go-login').onclick = (e) => { e.preventDefault(); switchTab('login'); };
        }
    };

    tabLogin.onclick = () => switchTab('login');
    tabRegister.onclick = () => switchTab('register');
    if(linkGoReg) linkGoReg.onclick = (e) => { e.preventDefault(); switchTab('register'); };
    
    // Exporta para uso global se necessário (pelo handleSubmit)
    window.switchTabGlobal = switchTab;
}

/* ── MOSTRAR/ESCONDER SENHA ── */
function setupPasswordToggle() {
    const buttons = document.querySelectorAll('.toggle-pw');
    buttons.forEach(btn => {
        btn.onclick = () => {
            const inputId = btn.getAttribute('data-target');
            const input = document.getElementById(inputId);
            const isPassword = input.type === 'password';
            input.type = isPassword ? 'text' : 'password';
            
            btn.innerHTML = isPassword 
                ? `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>`
                : `<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>`;
        };
    });
}

/* ── FORÇA DA SENHA ── */
function setupPasswordStrength() {
    const regPass = document.getElementById('reg-password');
    if(!regPass) return;

    regPass.oninput = () => {
        const val = regPass.value;
        const bars = [document.getElementById('bar1'), document.getElementById('bar2'), document.getElementById('bar3'), document.getElementById('bar4')];
        const label = document.getElementById('pw-label');
        
        bars.forEach(b => b.className = 'pw-bar');
        if (!val) { label.textContent = 'Digite uma senha'; return; }
        
        let score = Math.min(val.length / 2, 4);
        const levels = { 1: 'weak', 2: 'weak', 3: 'medium', 4: 'strong' };
        const level = levels[Math.floor(score)] || 'weak';
        
        for (let i = 0; i < score; i++) if(bars[i]) bars[i].classList.add(level);
        label.textContent = level === 'weak' ? 'Fraca' : level === 'medium' ? 'Média' : 'Forte';
    };
}

/* ── ENVIO DOS FORMULÁRIOS (API JAVA) ── */
function setupFormSubmissions() {
    const forms = [document.getElementById('form-login'), document.getElementById('form-register')];
    
    forms.forEach(form => {
        if(!form) return;
        form.onsubmit = async (e) => {
            e.preventDefault();
            const isRegister = form.id === 'form-register';
            const endpoint = isRegister ? "/cadastro" : "/login";
            
            let dados = {};
            if (isRegister) {
                const senha = document.getElementById('reg-password').value;
                const confirma = document.getElementById('reg-confirm').value;
                if (senha !== confirma) { alert("⚠️ As senhas não coincidem!"); return; }

                dados = {
                    nome: document.getElementById('reg-first').value + " " + document.getElementById('reg-last').value,
                    email: document.getElementById('reg-email').value,
                    senha: senha
                };
            } else {
                dados = {
                    email: document.getElementById('login-email').value,
                    senha: document.getElementById('login-password').value
                };
            }

            try {
                const response = await fetch(API_URL + endpoint, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(dados)
                });

                if (response.ok) {
                    if (isRegister) {
                        form.reset();
                        window.switchTabGlobal('login');
                    } else {
                        const result = await response.text();
                        localStorage.setItem('usuario', result);
                        window.location.href = "index.html";
                    }
                } else {
                    const erro = await response.text();
                    alert("⚠️ " + erro);
                }
            } catch (err) {
                alert("❌ Erro: Servidor Java desligado!");
            }
        };
    });
}