#ifdef GL_ES
precision mediump float;
precision mediump int;
#else
#define highp;
#endif
 
uniform sampler2D u_texture;
uniform sampler2D u_mask;
 
varying vec4 v_color;
varying vec2 v_texCoord;
 
void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoord);
    vec4 mask = texture2D(u_mask, v_texCoord);
    texColor.a *= mask.a;
    gl_FragColor = v_color * texColor;
}