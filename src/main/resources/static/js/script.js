// ================= MOBILE MENU =================

const menuBtn = document.querySelector(".menu-btn");
const navLinks = document.querySelector(".nav-links");

if (menuBtn) {
    menuBtn.addEventListener("click", () => {
        navLinks.classList.toggle("mobile-open");
    });
}


// ================= CART COUNTER =================

let cartCount = 0;

const cartCounter = document.querySelector(".cart-btn small");

function updateCartCounter() {
    if (cartCounter) {
        cartCounter.textContent = cartCount;
    }
}


// ================= EXPLORE BUTTON =================

const exploreBtn = document.querySelector(".hero-actions .primary-btn");

if (exploreBtn) {
    exploreBtn.addEventListener("click", () => {
        document.querySelector("#restaurants").scrollIntoView({
            behavior: "smooth"
        });
    });
}


// ================= VIEW RESTAURANTS =================

const viewRestaurantBtn = document.querySelector(".outline-btn");

if (viewRestaurantBtn) {
    viewRestaurantBtn.addEventListener("click", () => {
        document.querySelector("#restaurants").scrollIntoView({
            behavior: "smooth"
        });
    });
}


// ================= COUPON COPY =================

const copyCouponBtn = document.querySelector(".coupon-code button");

if (copyCouponBtn) {

    copyCouponBtn.addEventListener("click", async () => {

        const couponCode =
            document.querySelector(".coupon-code span").textContent;

        try {

            await navigator.clipboard.writeText(couponCode);

            copyCouponBtn.textContent = "Copied!";

            setTimeout(() => {
                copyCouponBtn.textContent = "Copy";
            }, 1800);

        } catch (error) {

            copyCouponBtn.textContent = "FOOD50";

            setTimeout(() => {
                copyCouponBtn.textContent = "Copy";
            }, 1800);

        }

    });

}


// ================= RESTAURANT HEART =================

const hearts = document.querySelectorAll(".heart");

hearts.forEach((heart) => {

    heart.addEventListener("click", () => {

        if (heart.textContent.trim() === "♡") {
            heart.textContent = "♥";
            heart.style.color = "#ff5a1f";
        } else {
            heart.textContent = "♡";
            heart.style.color = "";
        }

    });

});


// ================= CART BUTTON =================

const cartBtn = document.querySelector(".cart-btn");

if (cartBtn) {

    cartBtn.addEventListener("click", () => {

        if (cartCount === 0) {

            alert("Your cart is empty. Explore our restaurants!");

        } else {

            alert(`You have ${cartCount} item(s) in your cart.`);

        }

    });

}

// ================= LOGIN BUTTON =================

const loginBtn = document.querySelector(".login-btn");

if (loginBtn) {

    loginBtn.addEventListener("click", () => {

        alert("Login page coming next 🚀");

    });

}


// ================= SIGN UP BUTTON =================

const signupBtn = document.querySelector(".signup-btn");

if (signupBtn) {

    signupBtn.addEventListener("click", () => {

        alert("Signup page coming next 🚀");

    });

}


// ================= START ORDERING =================

const startOrderingBtn =
    document.querySelector(".white-btn");

if (startOrderingBtn) {

    startOrderingBtn.addEventListener("click", () => {

        document.querySelector("#restaurants").scrollIntoView({
            behavior: "smooth"
        });

    });

}


// ================= INITIALIZE =================

updateCartCounter();

// ================= LOCATION MAP =================

const locationModal = document.getElementById("locationModal");
const changeLocationBtn = document.querySelector(".change-location");
const closeLocation = document.getElementById("closeLocation");
const currentLocationBtn = document.getElementById("currentLocation");
const confirmLocationBtn = document.getElementById("confirmLocation");
const selectedAddress = document.getElementById("selectedAddress");
const locationSearch = document.getElementById("locationSearch");

let map;
let marker;
let deliveryPartnerMarker;
let deliveryAnimation = null;
let restaurantMarker;
let selectedLocation = null;
let routingControl = null;
let routeAnimationStarted = false;


// ================= OPEN LOCATION MODAL =================

if (changeLocationBtn) {

    changeLocationBtn.addEventListener("click", () => {

        locationModal.classList.add("active");

        setTimeout(() => {

            if (!map) {

                map = L.map("map").setView(
                    [28.6139, 77.2090],
                    12
                );

                L.tileLayer(
                    "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                    {
                        attribution: "&copy; OpenStreetMap contributors"
                    }
                ).addTo(map);


                // MAP CLICK

                map.on("click", function (e) {

                    setLocation(
                        e.latlng.lat,
                        e.latlng.lng
                    );

                });

            }

            map.invalidateSize();

        }, 200);

    });

}


// ================= SET LOCATION =================

async function setLocation(lat, lng) {

    selectedLocation = {
        lat: lat,
        lng: lng
    };

    if (routingControl) {
        try {
            map.removeControl(routingControl);
        } catch (error) {
            
        }

        routingControl = null;
    }


    // Marker

    if (marker) {

        marker.setLatLng([lat, lng]);

    } else {

        marker = L.marker([lat, lng])
            .addTo(map);

    }


    // Loading text

    selectedAddress.textContent =
        "Getting address...";
        showRestaurantDistance();
        getDeliveryPartnerLocation();
        fitTrackingMarkers();
        drawDeliveryRoute();


    // Reverse Geocoding

    try {

        const response = await fetch(
            `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`
        );

        const data = await response.json();

        if (data && data.display_name) {

            selectedAddress.textContent =
                data.display_name;

            selectedLocation.address =
                data.display_name;

        } else {

            selectedAddress.textContent =
                "Location selected";

        }

    } catch (error) {

        console.error("Address error:", error);

        selectedAddress.textContent =
            "Location selected";

    }

}

    // ================= DISTANCE CALCULATION =================

function calculateDistance(lat1, lon1, lat2, lon2) {

    const R = 6371; // Earth radius in KM

    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;

    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(lat1 * Math.PI / 180) *
        Math.cos(lat2 * Math.PI / 180) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);

    const c =
        2 * Math.atan2(
            Math.sqrt(a),
            Math.sqrt(1 - a)
        );

    return R * c;
}

    // ================= GET RESTAURANT LOCATION =================

async function getRestaurantLocation() {

    try {

        const response = await fetch(
            "/api/restaurants/2"
        );

        if (!response.ok) {
            throw new Error("Restaurant not found");
        }

        const restaurant = await response.json();

        showRestaurantMarker(
            restaurant.latitude,
            restaurant.longitude,
            restaurant.name
        );
        fitTrackingMarkers();
        drawDeliveryRoute();

        return {
            latitude: restaurant.latitude,
            longitude: restaurant.longitude
        };

    } catch (error) {

        console.error(
            "Restaurant location error:",
            error
        );

        return null;
    }
}

    // ================= SHOW RESTAURANT DISTANCE =================

async function showRestaurantDistance() {

    if (!selectedLocation) {
        return;
    }

    const restaurant =
        await getRestaurantLocation();

    if (!restaurant) {
        return;
    }

    const distance = calculateDistance(
        selectedLocation.lat,
        selectedLocation.lng,
        restaurant.latitude,
        restaurant.longitude
    );

    const distanceValue =
    document.getElementById("distanceValue");

    if (distanceValue) {

        distanceValue.textContent =
            `${distance.toFixed(2)} km away`;

    }

    
}
        // ================= DELIVERY PARTNER LOCATION =================

async function getDeliveryPartnerLocation() {

    try {

        const response = await fetch(
            "/api/delivery-partners/2/location"
        );

        if (!response.ok) {
            throw new Error("Delivery partner location not found");
        }

        const deliveryPartner = await response.json();

        
          
        let oldLocation = null;

        // Get current marker position BEFORE changing it
        if (deliveryPartnerMarker) {

            const currentPosition =
                deliveryPartnerMarker.getLatLng();

            oldLocation = {
                latitude: currentPosition.lat,
                longitude: currentPosition.lng
            };
        }

        // First time: create marker
        if (!deliveryPartnerMarker) {

            showDeliveryPartnerMarker(
                deliveryPartner.latitude,
                deliveryPartner.longitude,
                deliveryPartner.name
            );

        } else {

            // Move smoothly from old GPS to new GPS
            animateDeliveryPartner(
                oldLocation.latitude,
                oldLocation.longitude,
                deliveryPartner.latitude,
                deliveryPartner.longitude
            );
        }

        fitTrackingMarkers();
        drawDeliveryRoute();
        

        const partnerName =
            document.getElementById("deliveryPartnerName");

        const partnerStatus =
            document.getElementById("deliveryPartnerStatus");

        if (partnerName) {
            partnerName.textContent =
                deliveryPartner.name;
        }

        if (partnerStatus) {
            partnerStatus.textContent =
                deliveryPartner.status;
        }

        if (selectedLocation) {

            const distance =
                calculateDistance(
                    selectedLocation.lat,
                    selectedLocation.lng,
                    deliveryPartner.latitude,
                    deliveryPartner.longitude
                );

            const partnerDistance =
                document.getElementById(
                    "deliveryPartnerDistance"
                );

            if (partnerDistance) {
                partnerDistance.textContent =
                    `${distance.toFixed(2)} km away`;
            }

            
        }

        return {
            latitude: deliveryPartner.latitude,
            longitude: deliveryPartner.longitude,
            status: deliveryPartner.status,
            name: deliveryPartner.name
        };

    } catch (error) {

        console.error(
            "Delivery partner location error:",
            error
        );

        return null;
    }
}

    function animateDeliveryPartner(oldLat, oldLng, newLat, newLng) {

    if (!map || !deliveryPartnerMarker) {
        return;
    }

    if (deliveryAnimation) {
        cancelAnimationFrame(deliveryAnimation);
    }

    const duration = 30000;
    const startTime = performance.now();

    function animate(currentTime) {

        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);

        const lat =
            oldLat + (newLat - oldLat) * progress;

        const lng =
            oldLng + (newLng - oldLng) * progress;

        deliveryPartnerMarker.setLatLng([
            lat,
            lng
        ]);

        if (progress < 1) {
            deliveryAnimation =
                requestAnimationFrame(animate);
        } else {
            deliveryAnimation = null;
        }
    }

    deliveryAnimation =
        requestAnimationFrame(animate);
}

        function animateDeliveryAlongRoute(routeCoordinates) {

    if (!map || !deliveryPartnerMarker || !routeCoordinates) {
        return;
    }

    if (deliveryAnimation) {
        cancelAnimationFrame(deliveryAnimation);
    }

    let index = 0;

    const speed = 150; // milliseconds per road point

    function moveScooter() {

        if (index >= routeCoordinates.length) {
            deliveryAnimation = null;
            return;
        }

        const point = routeCoordinates[index];

        deliveryPartnerMarker.setLatLng([
            point.lat,
            point.lng
        ]);

        index++;

        setTimeout(() => {
            deliveryAnimation =
                requestAnimationFrame(moveScooter);
        }, speed);
    }

    moveScooter();
}

function showDeliveryPartnerMarker(latitude, longitude, name) {

    if (!map) return;

    const scooterIcon = L.divIcon({
        className: "delivery-scooter-marker",
        html: "🛵",
        iconSize: [40, 40],
        iconAnchor: [20, 20]
    });

    if (deliveryPartnerMarker) {

        deliveryPartnerMarker.setIcon(
            scooterIcon
        );

        deliveryPartnerMarker.setLatLng([
            latitude,
            longitude
        ]);

    } else {

        deliveryPartnerMarker = L.marker(
            [latitude, longitude],
            {
                icon: scooterIcon
            }
        ).addTo(map);
    }

    deliveryPartnerMarker.bindPopup(
        `🛵 <strong>${name}</strong><br>Delivery Partner`
    );
}

        // ================= RESTAURANT MARKER =================

function showRestaurantMarker(latitude, longitude, name) {

    if (!map) {
        return;
    }

    if (restaurantMarker) {

        restaurantMarker.setLatLng([
            latitude,
            longitude
        ]);

    } else {

        restaurantMarker =
            L.marker([
                latitude,
                longitude
            ]).addTo(map);
    }

    restaurantMarker.bindPopup(
        `🍽️ <strong>${name}</strong><br>Restaurant`
    );
}

        // Fit map around customer, restaurant and delivery partner
function fitTrackingMarkers() {

    if (!map) {
        return;
    }

    const points = [];

    if (marker) {
        points.push(marker.getLatLng());
    }

    if (restaurantMarker) {
        points.push(restaurantMarker.getLatLng());
    }

    if (deliveryPartnerMarker) {
        points.push(deliveryPartnerMarker.getLatLng());
    }

    if (points.length >= 2) {

        const bounds =
            L.latLngBounds(points);

        map.fitBounds(bounds, {
            padding: [50, 50]
        });
    }
}

        function drawDeliveryRoute() {

    if (!map || !selectedLocation ||
        !restaurantMarker || !deliveryPartnerMarker) {
        return;
    }

    
    const restaurantPoint =
        restaurantMarker.getLatLng();

    const deliveryPoint =
        deliveryPartnerMarker.getLatLng();

    const customerPoint = L.latLng(
        selectedLocation.lat,
        selectedLocation.lng
    );

    if (routingControl) {
    try {
        map.removeControl(routingControl);
    } catch (error) {
        
    }
    routingControl = null;
}

    routingControl = L.Routing.control({

        waypoints: [
            deliveryPoint,
            customerPoint
        ],

        routeWhileDragging: false,
        addWaypoints: false,
        draggableWaypoints: false,
        show: false,

        lineOptions: {
            styles: [
                {
                    color: "#ff5a1f",
                    weight: 6,
                    opacity: 0.85
                }
            ]
        },

        createMarker: function () {
            return null;
        }

    }).addTo(map);

    routingControl.on("routesfound", function (e) {
    const route = e.routes[0];

    const distanceKm =
        (route.summary.totalDistance / 1000).toFixed(2);

    const timeMinutes =
        Math.ceil(route.summary.totalTime / 60);

    const routeDistance =
        document.getElementById("routeDistance");

    const routeTime =
        document.getElementById("routeTime");

    if (routeDistance) {
        routeDistance.textContent =
            `${distanceKm} km`;
    }

    if (routeTime) {
        routeTime.textContent =
            `${timeMinutes} min`;
    }

});
}

getDeliveryPartnerLocation();



// ================= SEARCH LOCATION =================

if (locationSearch) {

    locationSearch.addEventListener(
        "keydown",
        async function (e) {

            if (e.key !== "Enter") {
                return;
            }

            const query = locationSearch.value.trim();

            if (!query) {
                return;
            }


            try {

                selectedAddress.textContent =
                    "Searching location...";


                const response = await fetch(
                    `https://nominatim.openstreetmap.org/search?format=jsonv2&q=${encodeURIComponent(query)}&limit=5`
                );

                const results = await response.json();


                if (!results.length) {

                    selectedAddress.textContent =
                        "Location not found";

                    return;

                }


                const place = results[0];

                const lat =
                    parseFloat(place.lat);

                const lon =
                    parseFloat(place.lon);


                map.setView(
                    [lat, lon],
                    16
                );


                setLocation(lat, lon);

            } catch (error) {

                console.error(
                    "Search error:",
                    error
                );

                selectedAddress.textContent =
                    "Unable to search location";

            }

        }
    );

}


// ================= CLOSE MODAL =================

if (closeLocation) {

    closeLocation.addEventListener(
        "click",
        () => {

            locationModal.classList.remove("active");

        }
    );

}


// ================= CURRENT LOCATION =================

if (currentLocationBtn) {

    currentLocationBtn.addEventListener(
        "click",
        () => {

            if (!navigator.geolocation) {

                alert(
                    "Location is not supported by your browser."
                );

                return;

            }


            currentLocationBtn.textContent =
                "📍 Detecting location...";


            navigator.geolocation.getCurrentPosition(

                (position) => {

                    const lat =
                        position.coords.latitude;

                    const lng =
                        position.coords.longitude;


                    map.setView(
                        [lat, lng],
                        17
                    );


                    setLocation(
                        lat,
                        lng
                    );


                    currentLocationBtn.textContent =
                        "📍 Use my current location";

                },

                () => {

                    alert(
                        "Unable to get your location. Please select it manually."
                    );


                    currentLocationBtn.textContent =
                        "📍 Use my current location";

                }

            );

        }
    );

}


// ================= CONFIRM LOCATION =================

if (confirmLocationBtn) {

    confirmLocationBtn.addEventListener(
        "click",
        () => {

            if (!selectedLocation) {

                alert(
                    "Please select a location first."
                );

                return;

            }


            const address =
                selectedLocation.address ||
                "Selected location";


            const mainLocation =
                document.querySelector(
                    ".location-info strong"
                );


            if (mainLocation) {

                mainLocation.textContent =
                    address;

            }


            locationModal.classList.remove(
                "active"
            );

        }
    );

}


// ================= CLOSE OUTSIDE =================

if (locationModal) {

    locationModal.addEventListener(
        "click",
        (e) => {

            if (e.target === locationModal) {

                locationModal.classList.remove(
                    "active"
                );

            }

        }
    );

}

// ================= LIVE DELIVERY TRACKING =================

setInterval(() => {

    if (selectedLocation) {
        getDeliveryPartnerLocation();
    }

}, 30000);

// ================= DELIVERY PARTNER GPS UPDATE =================

function updateDeliveryPartnerLocation() {

    if (!navigator.geolocation) {
        
        return;
    }

    navigator.geolocation.getCurrentPosition(
        async (position) => {

            const latitude =
                position.coords.latitude;

            const longitude =
                position.coords.longitude;

            try {

                const response = await fetch(
                    `/api/delivery-partners/2/location?latitude=${latitude}&longitude=${longitude}`,
                    {
                        method: "PUT",
                        headers: {
                            "Authorization":
                                "Bearer " + localStorage.getItem("foodiesToken")
                        }
                    }
                );

                if (!response.ok) {
                    throw new Error(
                        "Location update failed"
                    );
                }

                
            } catch (error) {

                console.error(
                    "GPS update error:",
                    error
                );
            }
        },

        (error) => {

            console.error(
                "GPS permission/error:",
                error
            );
        }
    );
}

updateDeliveryPartnerLocation();
setInterval(() => {
    updateDeliveryPartnerLocation();
}, 3000);

// =========================
// SIGNUP PASSWORD TOGGLE
// =========================

const togglePassword = document.getElementById("togglePassword");
const passwordInput = document.getElementById("password");

if (togglePassword && passwordInput) {

    togglePassword.addEventListener("click", function () {

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            togglePassword.textContent = "🙈";
        } else {
            passwordInput.type = "password";
            togglePassword.textContent = "👁";
        }

    });

}

// =========================
// SIGNUP PASSWORD STRENGTH
// =========================

const strengthProgress = document.getElementById("strengthProgress");
const strengthText = document.getElementById("strengthText");

if (passwordInput && strengthProgress && strengthText) {

    passwordInput.addEventListener("input", function () {

        const password = passwordInput.value;

        let strength = 0;

        if (password.length >= 6) strength++;
        if (password.length >= 8) strength++;
        if (/[A-Z]/.test(password)) strength++;
        if (/[0-9]/.test(password)) strength++;
        if (/[^A-Za-z0-9]/.test(password)) strength++;

        if (password.length === 0) {

            strengthProgress.style.width = "0%";
            strengthText.textContent = "Enter password";

        } else if (strength <= 2) {

            strengthProgress.style.width = "35%";
            strengthText.textContent = "Weak";

        } else if (strength <= 4) {

            strengthProgress.style.width = "70%";
            strengthText.textContent = "Medium";

        } else {

            strengthProgress.style.width = "100%";
            strengthText.textContent = "Strong";

        }

    });

}

// =========================
// SIGNUP FORM SUBMISSION
// =========================

const signupForm = document.getElementById("signupForm");

if (signupForm) {

signupForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const password = document.getElementById("password").value;
    const address = document.getElementById("address").value.trim();

    const userData = {
        name: name,
        email: email,
        phone: phone,
        password: password,
        address: address
    };

    try {

        const response = await fetch("/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userData)
        });

        const data = await response.json();

        if (response.ok) {

            alert("🎉 Account created successfully!");

            signupForm.reset();

            window.location.href = "login.html";

        } else {

            alert(data.message || "Signup failed. Please try again.");

        }

    } catch (error) {

        console.error("Signup error:", error);

        alert("Unable to connect to Foodies server.");

    }

});

}

// =========================
// LOGIN METHOD TOGGLE
// =========================

const emailLoginBtn = document.getElementById("emailLoginBtn");
const phoneLoginBtn = document.getElementById("phoneLoginBtn");

const emailLoginGroup = document.getElementById("emailLoginGroup");
const phoneLoginGroup = document.getElementById("phoneLoginGroup");

const loginEmail = document.getElementById("loginEmail");
const loginPhone = document.getElementById("loginPhone");

if (
    emailLoginBtn &&
    phoneLoginBtn &&
    emailLoginGroup &&
    phoneLoginGroup
) {

    emailLoginBtn.addEventListener("click", function () {

        emailLoginBtn.classList.add("active");
        phoneLoginBtn.classList.remove("active");

        emailLoginGroup.style.display = "block";
        phoneLoginGroup.style.display = "none";

        loginEmail.required = true;
        loginPhone.required = false;

        loginPhone.value = "";

    });


    phoneLoginBtn.addEventListener("click", function () {

        phoneLoginBtn.classList.add("active");
        emailLoginBtn.classList.remove("active");

        phoneLoginGroup.style.display = "block";
        emailLoginGroup.style.display = "none";

        loginPhone.required = true;
        loginEmail.required = false;

        loginEmail.value = "";

    });

}

// =========================
// LOGIN FORM SUBMISSION
// =========================

const loginForm = document.getElementById("loginForm");
const loginPassword = document.getElementById("loginPassword");

if (loginForm) {

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const password = loginPassword.value;

        let loginData = {};

        // Email Login
        if (
            emailLoginBtn &&
            emailLoginBtn.classList.contains("active")
        ) {

            const email = loginEmail.value.trim();

            if (!email) {
                alert("Please enter your email.");
                return;
            }

            loginData = {
                email: email,
                password: password
            };

        }

        // Phone Login
        else if (
            phoneLoginBtn &&
            phoneLoginBtn.classList.contains("active")
        ) {

            const phone = loginPhone.value.trim();

            if (!phone) {
                alert("Please enter your phone number.");
                return;
            }

            loginData = {
                phone: phone,
                password: password
            };

        }


        try {

            const response = await fetch(
                "/api/auth/login",
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(loginData)
                }
            );


            const token = await response.text();


            if (response.ok) {

                // Save JWT token
                localStorage.setItem(
                    "foodiesToken",
                    token
                );

                alert("🎉 Login successful!");

                

                // Get role from JWT
        const payload = JSON.parse(
            atob(
                token
                    .split(".")[1]
                    .replace(/-/g, "+")
                    .replace(/_/g, "/")
            )
        );

        const role = payload.role;

        if (role === "ADMIN") {

            window.location.href = "admin-dashboard.html";

        } else if (role === "DELIVERY_PARTNER") {

            window.location.href = "delivery-dashboard.html";

        } else {

            window.location.href = "customer-dashboard.html";

        }

            } else {

                alert(
                    token || "Invalid email/phone or password."
                );

            }

        } catch (error) {

            console.error(
                "Login error:",
                error
            );

            alert(
                "Unable to connect to Foodies server."
            );

        }

    });

}

// =========================
// LOGIN PASSWORD SHOW / HIDE
// =========================

const loginTogglePassword =
    document.getElementById("loginTogglePassword");

const loginPasswordInput =
    document.getElementById("loginPassword");

if (loginTogglePassword && loginPasswordInput) {

    loginTogglePassword.addEventListener("click", function () {

        if (loginPasswordInput.type === "password") {

            loginPasswordInput.type = "text";
            loginTogglePassword.textContent = "🙈";

        } else {

            loginPasswordInput.type = "password";
            loginTogglePassword.textContent = "👁";

        }

    });

}