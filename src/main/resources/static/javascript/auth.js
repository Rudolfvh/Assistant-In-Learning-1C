document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById("authModal");
    const openBtn = document.getElementById("openAuthModal");
    const closeBtn = modal.querySelector(".close");

    const loginContent = document.getElementById("loginContent");
    const registerContent = document.getElementById("registerContent");

    const switchToRegister = document.getElementById("switchToRegister");
    const switchToLogin = document.getElementById("switchToLogin");

    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");

    const authBtn = document.getElementById('openAuthModal'); // кнопка Войти/Профиль
    const avatarImg = document.getElementById('profileAvatar');

    // --- Открытие/закрытие модалки ---
    openBtn.onclick = () => {
        modal.style.display = "block";
        showLogin();
    };

    closeBtn.onclick = () => modal.style.display = "none";

    window.onclick = (event) => {
        if (event.target === modal) modal.style.display = "none";
    };

    // --- Переключение форм ---
    function showLogin() {
        loginContent.style.display = "block";
        registerContent.style.display = "none";
    }

    function showRegister() {
        loginContent.style.display = "none";
        registerContent.style.display = "block";
    }

    switchToRegister.onclick = showRegister;
    switchToLogin.onclick = showLogin;

    // --- Логин ---
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("modalUsername").value;
        const password = document.getElementById("modalPassword").value;

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password }),
                credentials: 'include'
            });

            if (!response.ok) throw new Error("Ошибка авторизации");

            modal.style.display = "none";
            await updateAuthButton();
        } catch (err) {
            alert(err.message);
        }
    });

    // --- Регистрация ---
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const data = {
            firstName: document.getElementById("regFirstName").value,
            lastName: document.getElementById("regLastName").value,
            email: document.getElementById("regEmail").value,
            username: document.getElementById("regUsername").value,
            password: document.getElementById("regPassword").value
        };

        try {
            const response = await fetch("/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data),
                credentials: 'include'
            });

            if (!response.ok) throw new Error("Ошибка регистрации");

            modal.style.display = "none";
            await updateAuthButton();
        } catch (err) {
            alert(err.message);
        }
    });

    // --- Обновление кнопки и аватара ---
async function updateAuthButton() {
    const authBtn = document.getElementById('openAuthModal');
    const avatarImg = document.getElementById('profileAvatar');
    const createBtn = document.getElementById('createTopicBtn'); // кнопка Создать тему

    try {
        const response = await fetch('/api/users/me', { credentials: 'include' });
        if (!response.ok) throw new Error('Не авторизован');

        const user = await response.json();

        // Кнопка "Профиль"
        authBtn.textContent = 'Профиль';
        authBtn.onclick = () => window.location.href = '/profile.html';

        // Аватар
        if (user.avatarBase64) {
            avatarImg.src = `data:image/png;base64,${user.avatarBase64}`;
            avatarImg.style.display = 'inline-block';
        } else {
            avatarImg.src = 'images/default-avatar.png';
            avatarImg.style.display = 'inline-block';
        }

        // Кнопка "Создать тему" видна только авторизованным
        if (createBtn) {
            createBtn.style.display = 'inline-block';
            createBtn.onclick = () => window.location.href = '../createTopic.html';
        }

    } catch {
        // Пользователь не авторизован
        authBtn.textContent = 'Войти';
        authBtn.onclick = () => {
            const modal = document.getElementById('authModal');
            if (modal) modal.style.display = "block";
        };

        avatarImg.style.display = 'none';
        if (createBtn) createBtn.style.display = 'none';
    }
}

    // --- Инициализация при загрузке страницы ---
    updateAuthButton();
});
