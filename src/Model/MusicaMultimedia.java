package Model;

public class MusicaMultimedia extends Musica{

    private String linkVideo;

    public MusicaMultimedia(){
        super();
        this.linkVideo="";
    }

    public MusicaMultimedia(String nome, String interprete, String nomeDaEditora, String letra, String melodia, String generoMusical, int duracao, int contador, String linkVideo) {
        super(nome, interprete, nomeDaEditora, letra, melodia, generoMusical, duracao, contador);
        this.linkVideo = linkVideo;
    }

    public MusicaMultimedia(MusicaMultimedia musicaMultimedia) {
        super(musicaMultimedia);
        this.linkVideo = musicaMultimedia.getLinkVideo();
    }

    public String getLinkVideo() {
        return this.linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    @Override
    public String toString() {return super.toString()+ "\nLink: " + this.linkVideo + "\n";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        MusicaMultimedia m = (MusicaMultimedia) o;
        return super.equals(m) && this.linkVideo.equals(m.getLinkVideo());
    }

    @Override
    public Musica clone() {
        return new MusicaMultimedia(this);
    }
}