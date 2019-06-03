//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform vec4 myColor;

//"in" varyings from our vertex shader
varying vec2 v_texCoord;

void main() {
// texture color
vec4 texColor = texture2D(u_texture, v_texCoord);
// tint it some color
texColor *= myColor;
//final color
gl_FragColor = texColor;
}