package es.unirioja.paw.jpa;

public class ClienteAvatarInfo {

    private final String imageSource;
    private final ClienteEntity cliente;
    
    public ClienteAvatarInfo(ClienteEntity cliente) {
        this.cliente = cliente;
        String avatar = cliente.getImagenAvatar();
        if (avatar == null || avatar.trim().isEmpty()) {
            this.imageSource = null;
        } else {
            this.imageSource = avatar.trim();
        }
    }
    
    public boolean isDefault() {
        return this.imageSource == null;
    }
    
    public boolean isRemoteURL() {
        if (this.imageSource == null) return false;
        String lower = this.imageSource.toLowerCase();
        return lower.startsWith("http://") || lower.startsWith("https://");
    }
    
    public String getImageSource() {
        return imageSource;
    }
}
