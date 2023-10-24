import { Navigate } from 'react-router-dom';
import { getAuth } from 'firebase/auth';

function PrivateRoute({ children }) {
    const auth = getAuth();
    const isUserAuthenticated = !!auth.currentUser;

    return isUserAuthenticated ? children : <Navigate to="/login" replace />;
}

export default PrivateRoute;
