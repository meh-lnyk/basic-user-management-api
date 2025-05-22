let token = null;

function login() {
  fetch("http://localhost:8081/api/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: document.getElementById("username").value,
      password: document.getElementById("password").value
    })
  })
  .then(res => {
    if (!res.ok) throw new Error("Вход запрещен");
    return res.json();
  })
  .then(data => {
    token = data.token;
    document.getElementById("users").style.display = "block";
    alert("Вход успешен");
  })
  .catch(err => alert(err.message));
}

function loadUsers() {
  fetch("http://localhost:8081/api/users", {
    headers: {
      "Authorization": "Bearer " + token
    }
  })
  .then(res => {
    if (!res.ok) throw new Error("Не смогли загрузить пользователей");
    return res.json();
  })
  .then(data => {
    document.getElementById("output").textContent = JSON.stringify(data, null, 2);
  })
  .catch(err => alert(err.message));
}

function showCreateUserForm() {
  document.getElementById("createUserModal").style.display = "block";
}

function closeCreateUserForm() {
  document.getElementById("createUserModal").style.display = "none";
}

function createUser() {
  const user = {
    firstName: document.getElementById("newFirstName").value,
    lastName: document.getElementById("newLastName").value,
    email: document.getElementById("newEmail").value,
    birthDate: document.getElementById("newBirthDate").value,
    phoneNumber: document.getElementById("newPhone").value
  };

  fetch("http://localhost:8081/api/users", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + token
    },
    body: JSON.stringify(user)
  })
  .then(res => {
    if (!res.ok) throw new Error("Ошибка при создании пользователя");
    return res.json();
  })
  .then(data => {
    alert("Пользователь создан");
    closeCreateUserForm();
    loadUsers();
  })
  .catch(err => alert(err.message));
}
