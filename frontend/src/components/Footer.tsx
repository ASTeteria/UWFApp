import styles from './Footer.module.css';

const Footer: React.FC = () => {
    return (
        <footer className={styles.footer}>
            <div className={styles.container}>
                <p>&copy; 2025 Ukrainian Wushu Federation. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;