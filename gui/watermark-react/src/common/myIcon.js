import Icon from '@ant-design/icons';
import React, { Component, useState } from 'react';
import ReactDOM from 'react-dom';

const FileSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>default_file</title><path d="M20.414,2H5V30H27V8.586ZM7,28V4H19v6h6V28Z" style={{fill:"#c5c5c5"}} /></svg>
);
const DirSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>default_folder</title><path d="M27.5,5.5H18.2L16.1,9.7H4.4V26.5H29.6V5.5Zm0,4.2H19.3l1.1-2.1h7.1Z" style={{fill:"#c09553"}} /></svg>
);
const CppSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_cpp</title><path d="M14.742,24.047a10.242,10.242,0,0,1-4.673.919A7.628,7.628,0,0,1,4.155,22.62,8.876,8.876,0,0,1,2,16.369,9.476,9.476,0,0,1,4.422,9.621a8.216,8.216,0,0,1,6.285-2.588,11.151,11.151,0,0,1,4.035.641v3.761A6.839,6.839,0,0,0,11,10.395,4.813,4.813,0,0,0,7.288,11.93a5.9,5.9,0,0,0-1.413,4.159A5.8,5.8,0,0,0,7.209,20.1a4.57,4.57,0,0,0,3.59,1.493,7.319,7.319,0,0,0,3.943-1.113Z" style={{fill:"#984c93"}} /><polygon points="17.112 14.829 17.112 12.485 19.456 12.485 19.456 14.829 21.8 14.829 21.8 17.172 19.456 17.172 19.456 19.515 17.112 19.515 17.112 17.172 14.77 17.172 14.77 14.828 17.112 14.829" style={{fill:"#984c93"}} /><polygon points="25.313 14.829 25.313 12.485 27.657 12.485 27.657 14.829 30 14.829 30 17.172 27.657 17.172 27.657 19.515 25.313 19.515 25.313 17.172 22.971 17.172 22.971 14.828 25.313 14.829" style={{fill:"#984c93"}} /></svg>
);
const HppSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_cppheader</title><path d="M2.395,25.5V6.5H4.727v6.817a5.212,5.212,0,0,1,4.121-1.892,5.558,5.558,0,0,1,2.657.6,3.539,3.539,0,0,1,1.614,1.665,7.677,7.677,0,0,1,.486,3.085V25.5H11.273V16.778a3.6,3.6,0,0,0-.758-2.547,2.819,2.819,0,0,0-2.145-.8,3.782,3.782,0,0,0-1.951.538,3.038,3.038,0,0,0-1.3,1.458,6.7,6.7,0,0,0-.389,2.54V25.5Z" style={{fill:"#984c93"}} /><polygon points="16.727 14.829 16.727 12.485 19.071 12.485 19.071 14.829 21.415 14.829 21.415 17.172 19.071 17.172 19.071 19.515 16.727 19.515 16.727 17.172 14.385 17.172 14.385 14.828 16.727 14.829" style={{fill:"#984c93"}} /><polygon points="24.928 14.829 24.928 12.485 27.272 12.485 27.272 14.829 29.615 14.829 29.615 17.172 27.272 17.172 27.272 19.515 24.928 19.515 24.928 17.172 22.585 17.172 22.585 14.828 24.928 14.829" style={{fill:"#984c93"}} /></svg>
);
const CcSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_cpp</title><path d="M14.742,24.047a10.242,10.242,0,0,1-4.673.919A7.628,7.628,0,0,1,4.155,22.62,8.876,8.876,0,0,1,2,16.369,9.476,9.476,0,0,1,4.422,9.621a8.216,8.216,0,0,1,6.285-2.588,11.151,11.151,0,0,1,4.035.641v3.761A6.839,6.839,0,0,0,11,10.395,4.813,4.813,0,0,0,7.288,11.93a5.9,5.9,0,0,0-1.413,4.159A5.8,5.8,0,0,0,7.209,20.1a4.57,4.57,0,0,0,3.59,1.493,7.319,7.319,0,0,0,3.943-1.113Z" style={{fill:"#984c93"}} /><polygon points="17.112 14.829 17.112 12.485 19.456 12.485 19.456 14.829 21.8 14.829 21.8 17.172 19.456 17.172 19.456 19.515 17.112 19.515 17.112 17.172 14.77 17.172 14.77 14.828 17.112 14.829" style={{fill:"#984c93"}} /><polygon points="25.313 14.829 25.313 12.485 27.657 12.485 27.657 14.829 30 14.829 30 17.172 27.657 17.172 27.657 19.515 25.313 19.515 25.313 17.172 22.971 17.172 22.971 14.828 25.313 14.829" style={{fill:"#984c93"}} /></svg>
);
const HSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_cppheader</title><path d="M2.395,25.5V6.5H4.727v6.817a5.212,5.212,0,0,1,4.121-1.892,5.558,5.558,0,0,1,2.657.6,3.539,3.539,0,0,1,1.614,1.665,7.677,7.677,0,0,1,.486,3.085V25.5H11.273V16.778a3.6,3.6,0,0,0-.758-2.547,2.819,2.819,0,0,0-2.145-.8,3.782,3.782,0,0,0-1.951.538,3.038,3.038,0,0,0-1.3,1.458,6.7,6.7,0,0,0-.389,2.54V25.5Z" style={{fill:"#984c93"}} /></svg>
);
const ShSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_shell</title><path d="M29.4,27.6H2.5V4.5H29.4Zm-25.9-1H28.4V5.5H3.5Z" style={{fill:"#d9b400"}} /><polygon points="6.077 19.316 5.522 18.484 10.366 15.255 5.479 11.184 6.12 10.416 12.035 15.344 6.077 19.316" style={{fill:"#d9b400"}}/><rect x="12.7" y="18.2" width="7.8" height="1" style={{fill:"#d9b400"}}/><rect x="2.5" y="5.5" width="26.9" height="1.9" style={{fill:"#d9b400"}} /></svg>
);
const MdSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_markdown</title><rect x="2.5" y="7.955" width="27" height="16.091" style={{fill:"none",stroke:"#755838"}} /><polygon points="5.909 20.636 5.909 11.364 8.636 11.364 11.364 14.773 14.091 11.364 16.818 11.364 16.818 20.636 14.091 20.636 14.091 15.318 11.364 18.727 8.636 15.318 8.636 20.636 5.909 20.636" style={{fill:"#755838"}} /><polygon points="22.955 20.636 18.864 16.136 21.591 16.136 21.591 11.364 24.318 11.364 24.318 16.136 27.045 16.136 22.955 20.636" style={{fill:"#755838"}} /></svg>
);
const PySvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><defs><linearGradient id="a" x1="-133.268" y1="-202.91" x2="-133.198" y2="-202.84" gradientTransform="translate(25243.061 38519.17) scale(189.38 189.81)" gradientUnits="userSpaceOnUse"><stop offset="0" stopColor="#387eb8" /><stop offset="1" stopColor="#366994" /></linearGradient><linearGradient id="b" x1="-133.575" y1="-203.203" x2="-133.495" y2="-203.133" gradientTransform="translate(25309.061 38583.42) scale(189.38 189.81)" gradientUnits="userSpaceOnUse"><stop offset="0" stopColor="#ffe052" /><stop offset="1" stopColor="#ffc331" /></linearGradient></defs><title>file_type_python</title><path d="M15.885,2.1c-7.1,0-6.651,3.07-6.651,3.07V8.36h6.752v1H6.545S2,8.8,2,16.005s4.013,6.912,4.013,6.912H8.33V19.556s-.13-4.013,3.9-4.013h6.762s3.772.06,3.772-3.652V5.8s.572-3.712-6.842-3.712h0ZM12.153,4.237a1.214,1.214,0,1,1-1.183,1.244v-.02a1.214,1.214,0,0,1,1.214-1.214h0Z" style={{fill:"url(#a)"}} /><path d="M16.085,29.91c7.1,0,6.651-3.08,6.651-3.08V23.65H15.985v-1h9.47S30,23.158,30,15.995s-4.013-6.912-4.013-6.912H23.64V12.4s.13,4.013-3.9,4.013H12.975S9.2,16.356,9.2,20.068V26.2s-.572,3.712,6.842,3.712h.04Zm3.732-2.147A1.214,1.214,0,1,1,21,26.519v.03a1.214,1.214,0,0,1-1.214,1.214h.03Z" style={{fill:"url(#b)"}} /></svg>
);
const CSvg = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><title>file_type_c</title><path d="M10.676,15.973a10.052,10.052,0,0,0,1.175,5.151,5.446,5.446,0,0,0,6.306,2.408,4.284,4.284,0,0,0,3.09-3.6c.107-.6.109-.61.109-.61,1.737.251,4.537.658,6.274.906l-.11.44a11.256,11.256,0,0,1-2.7,5.39,9.439,9.439,0,0,1-5.366,2.688,14.61,14.61,0,0,1-8.277-.819A10.151,10.151,0,0,1,5.4,21.687a16.225,16.225,0,0,1,.019-11.45,10.538,10.538,0,0,1,8.963-7.054,13.353,13.353,0,0,1,6.666.555,9.571,9.571,0,0,1,6.167,6.9c.094.352.114.417.114.417-1.932.351-4.319.8-6.238,1.215-.362-1.915-1.265-3.428-3.2-3.9a5.263,5.263,0,0,0-6.616,3.57,10.49,10.49,0,0,0-.385,1.439A12.31,12.31,0,0,0,10.676,15.973Z" style={{fill:"#005f91"}} /></svg>
);
const fileIcon = props => <Icon component={FileSvg} {...props} />;

const DirIcon = props => <Icon component={DirSvg} {...props} />;
const CppIcon = props => <Icon component={CppSvg} {...props} />;
const HppIcon = props => <Icon component={HppSvg} {...props} />;
const CcIcon = props => <Icon component={CcSvg} {...props} />;
const HIcon = props => <Icon component={HSvg} {...props} />;
const ShIcon = props => <Icon component={ShSvg} {...props} />;
const MdIcon = props => <Icon component={MdSvg} {...props} />;
const PyIcon = props => <Icon component={PySvg} {...props} />;
const CIcon = props => <Icon component={CSvg} {...props} />;

const getIcon = (type) => {
    switch (type) {
        case "cpp":
            return CppIcon();
        case "h":
            return HIcon();
        case "hpp":
            return HppIcon();
        case "cc":
            return CcIcon();
        case "c":
            return CIcon();
        case "sh":
            return ShIcon();
        case "md":
            return MdIcon();
        case "py":
            return PyIcon();
        case "dir":
            return DirIcon();
        default:
            return fileIcon();
    }
}

export default getIcon;