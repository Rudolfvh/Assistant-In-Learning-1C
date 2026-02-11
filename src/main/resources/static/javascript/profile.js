const profileInfo = document.getElementById('profileInfo');
const logoutBtn = document.getElementById('logoutBtn');
const backBtn = document.getElementById('backBtn');

backBtn.addEventListener('click', () => {
    window.location.href = '../index.html';
});

// Запрашиваем текущего пользователя с сервера
fetch('/api/users/me', { credentials: 'include' })
    .then(res => {
        if (!res.ok) throw new Error('Не авторизован');
        return res.json();
    })
    .then(user => {
        profileInfo.innerHTML = `
            <div><strong>Имя:</strong> ${user.firstName}</div>
            <div><strong>Фамилия:</strong> ${user.lastName}</div>
            <div><strong>Логин:</strong> ${user.username}</div>
            <div><strong>Почта:</strong> ${user.email}</div>
        `;
    })
    .catch(err => {
        profileInfo.innerHTML = `<p>${err.message}. Пожалуйста, войдите.</p>`;
    });

// Кнопка выхода
logoutBtn.addEventListener('click', () => {
    // Отправляем POST на logout, чтобы очистить сессию на сервере
    fetch('/api/auth/logout', { method: 'POST' })
        .finally(() => window.location.href = 'index.html'); // редирект на главную
});
