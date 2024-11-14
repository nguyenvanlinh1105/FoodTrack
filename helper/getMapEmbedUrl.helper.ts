import axios from 'axios';

const getMapEmbedUrl = async (address: string): Promise<string | null> => {
    const key=process.env.API_MAP_KEY;
    console.log(key);
    console.log(address);
    try {
        const response = await axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
            params: {
                address: address,
                key: process.env.API_MAP_KEY
            }
        });
        console.log(response);
        if (response.data.results.length > 0) {
            const { lat, lng } = response.data.results[0].geometry.location;
            const mapEmbedUrl = `https://www.google.com/maps/embed/v1/place?key=${process.env.API_MAP_KEY}&q=${lat},${lng}`;
            return mapEmbedUrl;
        } else {
            throw new Error('Không tìm thấy địa chỉ');
        }
    } catch (error) {
        console.error('Lỗi khi lấy URL bản đồ:', error);
        return null;
    }
};
export default getMapEmbedUrl;